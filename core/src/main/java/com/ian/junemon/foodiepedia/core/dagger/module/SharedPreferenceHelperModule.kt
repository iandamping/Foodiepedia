package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.cache.preference.PreferenceHelper
import com.ian.junemon.foodiepedia.core.cache.preference.PreferenceHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class SharedPreferenceHelperModule {

    @Binds
    abstract fun bindSharedPreferenceHelper(sharedPreferences: PreferenceHelperImpl): PreferenceHelper
}