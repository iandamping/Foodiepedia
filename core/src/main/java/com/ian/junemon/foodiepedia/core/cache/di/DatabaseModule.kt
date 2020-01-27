package com.ian.junemon.foodiepedia.core.cache.di

import android.app.Application
import androidx.room.Room
import com.ian.junemon.foodiepedia.core.cache.db.FoodDao
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 06,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DatabaseModule {

    @Provides
    @JvmStatic
    fun provideDb(app: Application): FoodDatabase {
        return Room
            .databaseBuilder(app, FoodDatabase::class.java, "foodiepedia.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @JvmStatic
    fun provideGameDao(db: FoodDatabase): FoodDao {
        return db.foodDao()
    }
}