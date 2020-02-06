package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.junemon.model.domain.FoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ian Damping on 05,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodCacheDataSourceImpl(var listOfFakeFood: MutableList<FoodCacheDomain>) :
    FoodCacheDataSource {

    var localListOfFakeFood: MutableList<FoodCacheDomain>? = null

    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return flowOf(localListOfFakeFood ?: listOfFakeFood)
    }

    override fun getCategirizeCache(foodCategory: String): Flow<List<FoodCacheDomain>> {
        return flowOf(localListOfFakeFood?.filter { it.foodCategory == foodCategory }
            ?: listOfFakeFood.filter { it.foodCategory == foodCategory })
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        localListOfFakeFood = listOfFakeFood
    }
}