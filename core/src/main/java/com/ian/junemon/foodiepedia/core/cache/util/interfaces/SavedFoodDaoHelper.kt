package com.ian.junemon.foodiepedia.core.cache.util.interfaces

import com.ian.junemon.foodiepedia.core.cache.model.SavedFood
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface SavedFoodDaoHelper {

    fun loadFood(): Flow<List<SavedFood>>

    fun loadCategorizeFood(foodCategory: String): Flow<List<SavedFood>>

    suspend fun deleteAllFood()

    suspend fun insertFood(vararg tagsData: SavedFood)

    suspend fun deleteSelectedId(selectedId: Int)
}