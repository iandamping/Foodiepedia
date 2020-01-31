package com.ian.junemon.foodiepedia

import android.app.Activity
import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponent
import com.ian.junemon.foodiepedia.core.dagger.component.DaggerCoreComponent
import com.ian.junemon.foodiepedia.core.worker.creator.FetcherWorkerFactoryImpl
import com.ian.junemon.foodiepedia.core.worker.setupReccuringWork
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.dagger.AppComponent
import com.ian.junemon.foodiepedia.dagger.DaggerActivityComponent
import com.ian.junemon.foodiepedia.dagger.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FoodApp : Application(), Configuration.Provider {


    private val appComponent: AppComponent by lazy {
        initializeAppComponent()
    }

    private val coreComponent: CoreComponent by lazy {
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

    private fun initializeCoreComponent(): CoreComponent {
        return DaggerCoreComponent.factory().injectApplication(this)
    }

    private fun initializeActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.factory().appComponent(appComponent)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()
}

fun Activity.activityComponent() = (application as FoodApp).activityComponent