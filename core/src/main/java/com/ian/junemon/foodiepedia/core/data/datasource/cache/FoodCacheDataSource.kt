package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow

interface FoodCacheDataSource {

    fun getCache(): Flow<List<FoodCacheDomain>>

    fun getCacheById(id:Int): Flow<DataSourceHelper<FoodCacheDomain>>

    fun getSavedDetailCache(): Flow<DataSourceHelper<List<SavedFoodCacheDomain>>>

    fun getCategorizeCache(foodCategory: String): Flow<DataSourceHelper<List<FoodCacheDomain>>>

    suspend fun setCache(vararg data: FoodCacheDomain)

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    suspend fun deleteSelectedId(selectedId: Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)

    suspend fun deleteFood()

    fun setFoodOfTheDayExpiredDate(data: String)

    fun getFoodOfTheDayExpiredDate(): String

    fun clearFoodOfTheDayExpiredDate()

    fun setFoodOfTheDay(data: String)

    fun getFoodOfTheDay(): String

    fun clearFoodOfTheDay()
}