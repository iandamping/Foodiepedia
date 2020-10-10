package com.ian.junemon.foodiepedia.dagger.module

import android.content.Context
import com.ian.junemon.foodiepedia.FoodApp
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: FoodApp): Context {
        return application.applicationContext
    }
}