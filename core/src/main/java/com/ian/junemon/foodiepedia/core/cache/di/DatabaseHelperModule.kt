package com.ian.junemon.foodiepedia.core.cache.di

import com.ian.junemon.foodiepedia.core.cache.util.classes.FoodDaoHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class DatabaseHelperModule {

    @Binds
    abstract fun bindDatabaseHelper(foodDatabaseHelper:FoodDaoHelperImpl):FoodDaoHelperImpl
}