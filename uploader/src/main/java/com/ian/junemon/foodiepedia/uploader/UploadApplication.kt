package com.ian.junemon.foodiepedia.uploader

import com.ian.junemon.foodiepedia.uploader.dagger.DaggerUploadComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

/**
 * Created by Ian Damping on 26,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerUploadComponent.builder().create(this)
    }
}
