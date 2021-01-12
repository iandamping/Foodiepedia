package com.ian.junemon.foodiepedia.core.data.datasource.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.FoodDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.SavedFoodDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.Food
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.SavedFood
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.UserProfile

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Database(entities = [Food::class, UserProfile::class, SavedFood::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun profileDao(): ProfileDao
    abstract fun savedFoodDao(): SavedFoodDao
}