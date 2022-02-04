package com.ian.junemon.foodiepedia.uploader

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by Ian Damping on 26,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@HiltAndroidApp
class UploadApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
