package com.ian.junemon.foodiepedia.di

import androidx.room.Room
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import org.koin.dsl.module.module

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
object DatabaseModule {
    val databaseModule = module {
        // Room Database instance
        single { Room.databaseBuilder(get(), FoodDatabase::class.java, "LocalFoodData").build() }
        // localDao instance (get instance from FoodDatabase)
        single { get<FoodDatabase>().foodDao() }
    }
}