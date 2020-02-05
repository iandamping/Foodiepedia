package com.ian.junemon.foodiepedia.core.datasource

import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.junemon.model.domain.FoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ian Damping on 05,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodCacheDataSource(var listOfFakeFood: MutableList<FoodCacheDomain>) : FoodCacheDataSource {

    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return flowOf(listOfFakeFood)
    }

    override fun getCategirizeCache(foodCategory: String): Flow<List<FoodCacheDomain>> {
       return flowOf(listOfFakeFood.filter { it.foodCategory == foodCategory })
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        listOfFakeFood.add(data[0])
    }
}