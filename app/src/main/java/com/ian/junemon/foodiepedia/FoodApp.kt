package com.ian.junemon.foodiepedia

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FoodApp : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    private fun delayedInit(app: Application) {
        applicationScope.launch {

            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        delayedInit(this)
    }
}