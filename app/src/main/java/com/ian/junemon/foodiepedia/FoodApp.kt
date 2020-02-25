package com.ian.junemon.foodiepedia

import android.app.Application
import androidx.work.Configuration
import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponent
import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponentProvider
import com.ian.junemon.foodiepedia.core.dagger.component.DaggerCoreComponent
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.dagger.ActivityComponentProvider
import com.ian.junemon.foodiepedia.dagger.AppComponent
import com.ian.junemon.foodiepedia.dagger.AppComponentProvider
import com.ian.junemon.foodiepedia.dagger.DaggerActivityComponent
import com.ian.junemon.foodiepedia.dagger.DaggerAppComponent
import com.ian.junemon.foodiepedia.feature.di.component.DaggerFoodComponent
import com.ian.junemon.foodiepedia.feature.di.component.FoodComponent
import com.ian.junemon.foodiepedia.feature.di.component.FoodComponentProvider
import timber.log.Timber

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FoodApp : Application(), ActivityComponentProvider, AppComponentProvider,
    CoreComponentProvider, FoodComponentProvider, Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    override fun provideActivityComponent(): ActivityComponent {
        return DaggerActivityComponent.factory().appComponent(provideAppComponent())
    }

    override fun provideAppComponent(): AppComponent {
        return DaggerAppComponent.factory().coreComponent(provideCoreComponent())
    }

    override fun provideCoreComponent(): CoreComponent {
        return DaggerCoreComponent.factory().injectApplication(this)
    }

    override fun provideFoodComponent(): FoodComponent {
        return DaggerFoodComponent.factory().create(provideActivityComponent())
    }
}
