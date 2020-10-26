package com.ian.junemon.foodiepedia.uploader.dagger.module

import com.ian.junemon.foodiepedia.core.dagger.scope.PerActivities
import com.ian.junemon.foodiepedia.uploader.ui.MainActivity
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
    @ContributesAndroidInjector(
        modules = [  // fragments
            UploadModule::class]
    )
    abstract fun mainActivity(): MainActivity
}