package com.example.junemon.foodapi_mvvm.data.local_data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
@Database(entities = [LocalFoodData::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {
    abstract fun foodDao(): LocalFoodDao
}