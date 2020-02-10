package com.ian.junemon.foodiepedia.data.data.fake

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 07,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodRepository(
    private val remoteDataSource: FoodRemoteDataSource,
    private val cacheDataSource: FoodCacheDataSource
) : FoodRepository {

    @ExperimentalCoroutinesApi
    override suspend fun foodPrefetch(): Flow<WorkerResult<Nothing>> {
        val responseStatus = remoteDataSource.getFirebaseData()
        return callbackFlow {
            responseStatus.collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceError -> {
                        if (!this@callbackFlow.channel.isClosedForSend) {
                            offer(WorkerResult.ErrorWork(data.exception))
                        }
                    }
                    is DataHelper.RemoteSourceValue -> {
                        cacheDataSource.setCache(*data.data.mapRemoteToCacheDomain().toTypedArray())
                        offer(WorkerResult.SuccessWork)
                    }
                }

            }
        }
    }

    override fun getCache(): LiveData<Results<List<FoodCacheDomain>>> {
        return liveData(Dispatchers.IO) {

            val responseStatus = remoteDataSource.getFirebaseData()

            responseStatus.collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceError -> {
                        emitSource(cacheDataSource.getCache().map {
                            Results.Error(exception = data.exception, cache = it)
                        }.asLiveData())
                    }
                    is DataHelper.RemoteSourceValue -> {
                        // because i dont use real db, i try to save it the data first by calling this setCache func
                        cacheDataSource.setCache(*data.data.mapRemoteToCacheDomain().toTypedArray())
                        emitSource(cacheDataSource.getCache().map { Results.Success(it) }.asLiveData())
                    }
                }
            }
        }
    }

    override fun getCategorizeCache(category: String): LiveData<List<FoodCacheDomain>> {
        return liveData {
            val responseStatus = remoteDataSource.getFirebaseData()
            responseStatus.collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceValue -> {
                        // because i dont use real db, i try to save it the data first by calling this setCache func
                        cacheDataSource.setCache(*data.data.mapRemoteToCacheDomain().toTypedArray())
                        emitSource(cacheDataSource.getCategirizeCache(category).asLiveData())
                    }
                }
            }
        }
    }

    override fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> = liveData {
        val pushStatus = remoteDataSource.uploadFirebaseData(data, imageUri)
        when (pushStatus) {
            is FirebaseResult.SuccessPush -> emit(FirebaseResult.SuccessPush)

            is FirebaseResult.ErrorPush -> emit(FirebaseResult.ErrorPush(pushStatus.exception))
        }
    }
}