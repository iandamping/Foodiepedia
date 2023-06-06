package com.ian.junemon.foodiepedia.core.data.repository

import android.net.Uri
import androidx.annotation.AnyThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.core.dagger.qualifier.ApplicationNonScope
import com.ian.junemon.foodiepedia.core.dagger.qualifier.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.data.NetworkBoundResource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.util.DataConstant.APPLICATION_ERROR
import com.ian.junemon.foodiepedia.core.util.DataConstant.ERROR_EMPTY_DATA
import com.ian.junemon.foodiepedia.core.util.fromJsonHelper
import com.ian.junemon.foodiepedia.core.util.mapRemoteToCacheDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRepositoryImpl @Inject constructor(
    @ApplicationNonScope private val scope: CoroutineScope,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    private val remoteDataSource: FoodRemoteDataSource,
    private val cacheDataSource: FoodCacheDataSource
) : FoodRepository {

    companion object {
        private val CACHE_EXPIRY:Long = TimeUnit.DAYS.toMillis(1)
    }

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY


    @AnyThread
    private suspend fun List<FoodRemoteDomain>.applyMainSafeSort() =
        withContext(defaultDispatcher) {
            this@applyMainSafeSort.applySort()
        }

    private fun List<FoodRemoteDomain>.applySort(): List<FoodCacheDomain> {
        return this.mapRemoteToCacheDomain()
    }

    override fun prefetchData(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return object : NetworkBoundResource<List<FoodCacheDomain>, List<FoodRemoteDomain>>(){

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

    override fun getCache(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return cacheDataSource.getCache().map {
            if (it.isEmpty()){
                RepositoryData.Error(ERROR_EMPTY_DATA)
            } else {
                RepositoryData.Success(it)
            }
        }
    }

    override fun getCacheById(id: Int): Flow<RepositoryData<FoodCacheDomain>> {
        return cacheDataSource.getCacheById(id).map {
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

    override fun getFoodOfTheDay(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        if (cacheDataSource.getFoodOfTheDayExpiredDate().isEmpty()){
            cacheDataSource.setFoodOfTheDayExpiredDate(System.currentTimeMillis().toString())
            return cacheDataSource.getCache().map {
                if (it.isEmpty()){
                    RepositoryData.Error(ERROR_EMPTY_DATA)
                } else {
                    cacheDataSource.setFoodOfTheDay(Gson().toJson(it.shuffled().take(5)))
                    RepositoryData.Success(Gson().fromJsonHelper(cacheDataSource.getFoodOfTheDay()))
                }
            }
        } else {
            val tmp = cacheDataSource.getFoodOfTheDayExpiredDate().toLong()
            if (tmp.isExpired()){
                cacheDataSource.clearFoodOfTheDayExpiredDate()
                cacheDataSource.setFoodOfTheDayExpiredDate(System.currentTimeMillis().toString())
                return cacheDataSource.getCache().map {
                    if (it.isEmpty()){
                        RepositoryData.Error(ERROR_EMPTY_DATA)
                    } else {
                        cacheDataSource.clearFoodOfTheDay()
                        cacheDataSource.setFoodOfTheDay(Gson().toJson(it.shuffled().take(5)))
                        RepositoryData.Success(Gson().fromJsonHelper(cacheDataSource.getFoodOfTheDay()))
                    }
                }
            }else{
                return cacheDataSource.getCache().map {
                    if (it.isEmpty()){
                        RepositoryData.Error(ERROR_EMPTY_DATA)
                    } else {
                        RepositoryData.Success(Gson().fromJsonHelper(cacheDataSource.getFoodOfTheDay()))
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
            when (val pushStatus = remoteDataSource.uploadFirebaseData(data, imageUri)) {
                is FirebaseResult.SuccessPush -> emit(FirebaseResult.SuccessPush)

                is FirebaseResult.ErrorPush -> emit(FirebaseResult.ErrorPush(pushStatus.exception))
            }
        }
    }

    override fun setCache(vararg data: FoodCacheDomain) {
        scope.launch {
            cacheDataSource.setCache(*data)
        }
    }

    override fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        scope.launch {
            cacheDataSource.setCacheDetailFood(*data)
        }

    }

    override fun deleteSelectedId(selectedId: Int) {
        scope.launch { cacheDataSource.deleteSelectedId(selectedId) }


    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return cacheDataSource.loadSharedPreferenceFilter()
    }

    override fun setSharedPreferenceFilter(data: String) {
        scope.launch { cacheDataSource.setSharedPreferenceFilter(data) }


    }

    override fun deleteFood() {
        scope.launch { cacheDataSource.deleteFood() }
    }
}