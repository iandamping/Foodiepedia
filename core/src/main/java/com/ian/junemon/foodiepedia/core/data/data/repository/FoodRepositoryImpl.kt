package com.ian.junemon.foodiepedia.core.data.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.di.IoDispatcher
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRepositoryImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val remoteDataSource: FoodRemoteDataSource,
    private val cacheDataSource: FoodCacheDataSource
) : FoodRepository {
    override fun getCache(): LiveData<Results<List<FoodCacheDomain>>> {
        return liveData(ioDispatcher) {
            val disposables = emitSource(cacheDataSource.getCache().map {
                Results.Loading
            }.asLiveData())

            val responseStatus = remoteDataSource.getFirebaseData()

            responseStatus.collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceError -> {
                        emitSource(cacheDataSource.getCache().map {
                            Results.Error(exception = data.exception, cache = it)
                        }.asLiveData())
                    }
                    is DataHelper.RemoteSourceValue -> {
                        check(data.data.isNotEmpty()) {
                            " data is empty "
                        }
                        // Stop the previous emission to avoid dispatching the updated user
                        // as `loading`.
                        disposables.dispose()
                        cacheDataSource.setCache(data.data.mapRemoteToCacheDomain())
                        emitSource(cacheDataSource.getCache().map { Results.Success(it) }.asLiveData())
                    }
                }
            }
        }
    }

    override fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> {
        return liveData {
            val pushStatus = remoteDataSource.uploadFirebaseData(data, imageUri)
            when (pushStatus) {
                is FirebaseResult.SuccessPush -> emit(FirebaseResult.SuccessPush)

                is FirebaseResult.ErrorPush -> emit(FirebaseResult.ErrorPush(pushStatus.exception))
            }
        }
    }
}