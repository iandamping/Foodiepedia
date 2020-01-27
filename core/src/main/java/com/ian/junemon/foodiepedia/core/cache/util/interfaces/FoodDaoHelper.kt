package com.ian.junemon.foodiepedia.core.cache.util.interfaces

import com.ian.junemon.foodiepedia.core.cache.model.Food
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodDaoHelper {
    fun loadFood(): Flow<List<Food>>

    suspend fun deleteAllFood()

    suspend fun insertFood(vararg tagsData: Food)
}