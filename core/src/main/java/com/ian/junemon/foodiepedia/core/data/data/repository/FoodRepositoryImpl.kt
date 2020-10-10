package com.ian.junemon.foodiepedia.core.data.data.repository

import android.net.Uri
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.dagger.module.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.data.dto.mapRemoteToCacheDomain
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRepositoryImpl @Inject constructor(
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val remoteDataSource: FoodRemoteDataSource,
    private val cacheDataSource: FoodCacheDataSource
) : FoodRepository {

    @AnyThread
    private suspend fun List<FoodRemoteDomain>.applyMainSafeSort() =
        withContext(defaultDispatcher) {
            this@applyMainSafeSort.applySort()
        }

    private fun List<FoodRemoteDomain>.applySort(): List<FoodCacheDomain> {
        return this.mapRemoteToCacheDomain()
    }


    override fun homeFoodPrefetch(): LiveData<Results<List<FoodCacheDomain>>> {
        return flow {
            emitAll(remoteDataSource.getFirebaseDatas().flatMapLatest { firebaseResult ->
                when (firebaseResult) {
                    is DataHelper.RemoteSourceError -> {
                        flowOf(Results.Error(exception = firebaseResult.exception))
                    }

                    is DataHelper.RemoteSourceValue -> {
                        with(cacheDataSource){
                            cacheDataSource.setCache(*firebaseResult.data.applyMainSafeSort().toTypedArray())
                            cacheDataSource.getCache().map { Results.Success(it) }
                        }

                    }
                    else -> {
                        cacheDataSource.getCache().map {
                            Results.Loading(cache = it)
                        }
                    }
                }
            })
        }.asLiveData()
    }

    override fun getCache():LiveData<List<FoodCacheDomain>> {
        return cacheDataSource.getCache().asLiveData()
    }

    override fun getSavedDetailCache(): LiveData<List<SavedFoodCacheDomain>> {
        return cacheDataSource.getSavedDetailCache().asLiveData()
    }

    override fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> {
        return liveData {
            when (val pushStatus = remoteDataSource.uploadFirebaseData(data, imageUri)) {
                is FirebaseResult.SuccessPush -> emit(FirebaseResult.SuccessPush)

                is FirebaseResult.ErrorPush -> emit(FirebaseResult.ErrorPush(pushStatus.exception))
            }
        }
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        cacheDataSource.setCacheDetailFood(*data)
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        cacheDataSource.deleteSelectedId(selectedId)
    }

    override fun loadSharedPreferenceFilter(): String {
        return cacheDataSource.loadSharedPreferenceFilter()
    }

    override fun setSharedPreferenceFilter(data: String) {
        cacheDataSource.setSharedPreferenceFilter(data)
    }
}