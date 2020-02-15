package com.ian.junemon.foodiepedia.core.cache.di

import android.app.Application
import androidx.room.Room
import com.ian.junemon.foodiepedia.core.cache.db.FoodDao
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.db.ProfileDao
import com.ian.junemon.foodiepedia.core.cache.db.SavedFoodDao
import com.ian.junemon.foodiepedia.core.cache.util.PreferenceHelper
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

    @Provides
    @JvmStatic
    fun providePreferenceHelper(app: Application): PreferenceHelper {
        return PreferenceHelper(app)
    }
}