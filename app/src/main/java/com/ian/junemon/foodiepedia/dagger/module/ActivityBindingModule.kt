package com.ian.junemon.foodiepedia.dagger.module

import com.ian.junemon.foodiepedia.dagger.scoped.ActivityScoped
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

    @ContributesAndroidInjector
    @ActivityScoped
    abstract fun splashActivity(): SplashActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [  // fragments
            FoodModule::class]
    )
    abstract fun mainActivity(): MainActivity
}