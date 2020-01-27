package com.ian.junemon.foodiepedia.core.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ian.junemon.foodiepedia.core.cache.model.Food

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase: RoomDatabase() {
    abstract fun foodDao():FoodDao
}