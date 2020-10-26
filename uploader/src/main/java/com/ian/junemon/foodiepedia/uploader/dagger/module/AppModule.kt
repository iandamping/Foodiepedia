package com.ian.junemon.foodiepedia.uploader.dagger.module

import android.content.Context
import com.ian.junemon.foodiepedia.uploader.UploadApplication
import dagger.Module
import dagger.Provides

@Module
class AppModule {

    @Provides
    fun provideContext(application: UploadApplication): Context {
        return application.applicationContext
    }
}