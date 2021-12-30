package com.ian.junemon.foodiepedia.core.data.repository

import android.net.Uri
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.dagger.qualifier.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.data.NetworkBoundResource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.model.*
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.util.DataConstant.APPLICATION_ERROR
import com.ian.junemon.foodiepedia.core.util.DataConstant.ERROR_EMPTY_DATA
import com.ian.junemon.foodiepedia.core.util.mapRemoteToCacheDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
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

    override fun prefetchData(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return remoteDataSource.getFirebaseData().map {  responseStatus ->
            when(responseStatus){
                is DataSourceHelper.DataSourceError ->{
                    RepositoryData.Error(responseStatus.exception.localizedMessage ?: APPLICATION_ERROR)
                }
                is DataSourceHelper.DataSourceValue ->{
                    RepositoryData.Success(responseStatus.data.applyMainSafeSort())
                }
            }
        }
    }

    override fun getCache(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return cacheDataSource.getCache().map {
            if (it.isEmpty()){
                RepositoryData.Error(ERROR_EMPTY_DATA)
            } else {
                RepositoryData.Success(it)
            }

        }
    }

    override fun getCategorizeCache(foodCategory: String): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return cacheDataSource.getCategorizeCache(foodCategory).map {
            when(it){
                is DataSourceHelper.DataSourceError ->{
                    RepositoryData.Error(it.exception.localizedMessage ?: APPLICATION_ERROR)
                }
                is DataSourceHelper.DataSourceValue ->{
                    RepositoryData.Success(it.data)
                }
            }
        }
    }

    override fun getSavedDetailCache(): Flow<RepositoryData<List<SavedFoodCacheDomain>>> {
        return cacheDataSource.getSavedDetailCache().map {
            when (it) {
                is DataSourceHelper.DataSourceValue -> {
                    RepositoryData.Success(it.data)
                }
                is DataSourceHelper.DataSourceError -> {
                    RepositoryData.Error(it.exception.localizedMessage ?: APPLICATION_ERROR)
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

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        cacheDataSource.setCache(*data)
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