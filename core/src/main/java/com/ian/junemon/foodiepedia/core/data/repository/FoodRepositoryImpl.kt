package com.ian.junemon.foodiepedia.core.data.repository

import android.net.Uri
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.dagger.module.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.data.NetworkBoundResource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.util.mapRemoteToCacheDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
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

    override fun getCache(): Flow<Results<List<FoodCacheDomain>>> {
        return object : NetworkBoundResource<List<FoodCacheDomain>, List<FoodRemoteDomain>>() {
            override fun loadFromDB(): Flow<List<FoodCacheDomain>> {
                return cacheDataSource.getCache()
            }

            override fun shouldFetch(data: List<FoodCacheDomain>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<DataSourceHelper<List<FoodRemoteDomain>>> {
                return remoteDataSource.getFirebaseData()
            }

            override suspend fun saveCallResult(data: List<FoodRemoteDomain>) {
                cacheDataSource.setCache(*data.applyMainSafeSort().toTypedArray())
            }
        }.asFlow()
    }

    override fun getCategorizeCache(foodCategory: String): Flow<Results<List<FoodCacheDomain>>> {
        return cacheDataSource.getCategorizeCache(foodCategory).map {
            when(it){
                is DataSourceHelper.DataSourceError ->{
                    Results.Error(it.exception)
                }
                is DataSourceHelper.DataSourceValue ->{
                    Results.Success(it.data)
                }
            }
        }
    }

    override fun getSavedDetailCache(): Flow<Results<List<SavedFoodCacheDomain>>> {
        return cacheDataSource.getSavedDetailCache().map {
            when (it) {
                is DataSourceHelper.DataSourceValue -> {
                    Results.Success(it.data)
                }
                is DataSourceHelper.DataSourceError -> {
                    Results.Error(it.exception)
                }
            }
        }
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

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return cacheDataSource.loadSharedPreferenceFilter()
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        cacheDataSource.setSharedPreferenceFilter(data)
    }
}