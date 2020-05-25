package com.ian.junemon.foodiepedia.feature.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 25,May,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object FoodHelperModule {

    @Provides
    @JvmStatic
    fun provideGson(): Gson {
        return Gson()
    }
}