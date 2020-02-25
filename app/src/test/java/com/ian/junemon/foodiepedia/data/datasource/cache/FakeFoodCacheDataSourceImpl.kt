package com.ian.junemon.foodiepedia.data.datasource.cache

import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ian Damping on 05,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodCacheDataSourceImpl :
    FoodCacheDataSource {

    private var localListOfFakeFood: MutableList<FoodCacheDomain> = mutableListOf()
    private var localListOfSavedFakeFood: MutableList<SavedFoodCacheDomain> = mutableListOf()

    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return flowOf(localListOfFakeFood)
    }

    override fun getSavedDetailCache(): Flow<List<SavedFoodCacheDomain>> {
        return flowOf(localListOfSavedFakeFood)
    }

    override fun getCategirizeCache(foodCategory: String): Flow<List<FoodCacheDomain>> {
        return flowOf(localListOfFakeFood.filter { it.foodCategory == foodCategory })
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        localListOfFakeFood.add(data[0])
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        localListOfSavedFakeFood.add(data[0])
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
    }
}