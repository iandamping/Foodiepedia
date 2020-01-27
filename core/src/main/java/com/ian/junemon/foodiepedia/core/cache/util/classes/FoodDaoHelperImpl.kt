package com.ian.junemon.foodiepedia.core.cache.util.classes

import com.ian.junemon.foodiepedia.core.cache.db.FoodDao
import com.ian.junemon.foodiepedia.core.cache.model.Food
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodDaoHelperImpl @Inject constructor(private val foodDao:FoodDao):FoodDaoHelper {
    override fun loadFood(): Flow<List<Food>> {
        return foodDao.loadFood()
    }

    override suspend fun deleteAllFood() {
       foodDao.deleteAllFood()
    }

    override suspend fun insertFood(vararg tagsData: Food) {
        foodDao.insertFood(*tagsData)
    }
}