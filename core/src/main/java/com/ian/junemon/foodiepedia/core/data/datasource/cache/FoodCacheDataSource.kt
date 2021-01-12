package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.junemon.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow

interface FoodCacheDataSource {

    fun getCache(): Flow<List<FoodCacheDomain>>

    fun getSavedDetailCache(): Flow<DataSourceHelper<List<SavedFoodCacheDomain>>>

    fun getCategorizeCache(foodCategory: String): Flow<DataSourceHelper<List<FoodCacheDomain>>>

    suspend fun setCache(vararg data: FoodCacheDomain)

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    suspend fun deleteSelectedId(selectedId: Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)
}