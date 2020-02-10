package com.ian.junemon.foodiepedia.core.cache.util.classes

import com.ian.junemon.foodiepedia.core.cache.db.SavedFoodDao
import com.ian.junemon.foodiepedia.core.cache.model.SavedFood
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.SavedFoodDaoHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SavedFoodDaoHelperImpl @Inject constructor(private val savedFood:SavedFoodDao):
    SavedFoodDaoHelper {
    override fun loadFood(): Flow<List<SavedFood>> {
        return savedFood.loadFood()
    }

    override fun loadCategorizeFood(foodCategory: String): Flow<List<SavedFood>> {
      return savedFood.loadFoodByCategory(foodCategory)
    }

    override suspend fun deleteAllFood() {
        savedFood.deleteAllFood()
    }

    override suspend fun insertFood(vararg tagsData: SavedFood) {
        savedFood.insertFood(*tagsData)
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        savedFood.deleteSelectedId(selectedId)
    }
}