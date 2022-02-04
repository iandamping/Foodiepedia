package com.ian.junemon.foodiepedia

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

@HiltAndroidApp
class FoodApp: Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}
