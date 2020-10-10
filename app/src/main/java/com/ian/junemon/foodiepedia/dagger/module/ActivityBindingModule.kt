package com.ian.junemon.foodiepedia.dagger.module

import com.ian.junemon.foodiepedia.core.dagger.scope.PerActivities
import com.ian.junemon.foodiepedia.ui.MainActivity
import com.ian.junemon.foodiepedia.ui.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Ian Damping on 10,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ActivityBindingModule {

    @PerActivities
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @PerActivities
    @ContributesAndroidInjector(
        modules = [  // fragments
            FoodModule::class]
    )
    abstract fun mainActivity(): MainActivity
}