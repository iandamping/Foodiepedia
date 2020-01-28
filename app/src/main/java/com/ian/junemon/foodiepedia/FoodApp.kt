package com.ian.junemon.foodiepedia

import android.app.Activity
import android.app.Application
import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponent
import com.ian.junemon.foodiepedia.core.dagger.component.DaggerCoreComponent
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.dagger.AppComponent
import com.ian.junemon.foodiepedia.dagger.DaggerActivityComponent
import com.ian.junemon.foodiepedia.dagger.DaggerAppComponent
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
    val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    val coreComponent: CoreComponent by lazy {
        initializeCoreComponent()
    }

    val activityComponent: ActivityComponent by lazy {
        initializeActivityComponent()
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initializeAppComponent(): AppComponent {
        return DaggerAppComponent.factory().coreComponent(coreComponent)
    }

    private fun initializeCoreComponent():CoreComponent{
        return DaggerCoreComponent.factory().injectApplication(this)
    }

    private fun initializeActivityComponent():ActivityComponent{
        return DaggerActivityComponent.factory().appComponent(appComponent)
    }
}

fun Activity.activityComponent() = (application as FoodApp).activityComponent