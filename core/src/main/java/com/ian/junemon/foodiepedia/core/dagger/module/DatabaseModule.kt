package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import androidx.room.Room
import com.ian.junemon.foodiepedia.core.cache.db.dao.FoodDao
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.cache.db.dao.SavedFoodDao
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
    fun provideDb(app: Context): FoodDatabase {
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

    @Provides
    @JvmStatic
    fun provideProfileDao(db: FoodDatabase): ProfileDao {
        return db.profileDao()
    }

    @Provides
    @JvmStatic
    fun provideSavedFoodDao(db: FoodDatabase): SavedFoodDao {
        return db.savedFoodDao()
    }
}