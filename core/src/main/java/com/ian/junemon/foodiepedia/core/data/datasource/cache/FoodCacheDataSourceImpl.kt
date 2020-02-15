package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToCacheDomain
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDatabase
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDetailDatabase
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.SavedFoodDaoHelper
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodCacheDataSourceImpl @Inject constructor(
    private val foodDao: FoodDaoHelper,
    private val savedFoodDao: SavedFoodDaoHelper
) : FoodCacheDataSource {
    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return foodDao.loadFood().map { it.mapToCacheDomain() }
    }

    override fun getSavedDetailCache(): Flow<List<SavedFoodCacheDomain>> {
        return savedFoodDao.loadFood().map { it.mapToDetailDatabase() }
    }

    override fun getCategirizeCache(foodCategory: String): Flow<List<FoodCacheDomain>> {
        return foodDao.loadCategorizeFood(foodCategory).map { it.mapToCacheDomain() }
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        foodDao.insertFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        savedFoodDao.insertFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        savedFoodDao.deleteSelectedId(selectedId)
    }
}