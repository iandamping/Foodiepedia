package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import androidx.room.Room
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.FoodDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.SavedFoodDao
import com.ian.junemon.foodiepedia.core.util.DataConstant.DATABASE_NAME
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
    fun provideDb(app: Context): FoodDatabase {
        return Room
            .databaseBuilder(app, FoodDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGameDao(db: FoodDatabase): FoodDao {
        return db.foodDao()
    }

    @Provides
    fun provideProfileDao(db: FoodDatabase): ProfileDao {
        return db.profileDao()
    }

    @Provides
    fun provideSavedFoodDao(db: FoodDatabase): SavedFoodDao {
        return db.savedFoodDao()
    }
}