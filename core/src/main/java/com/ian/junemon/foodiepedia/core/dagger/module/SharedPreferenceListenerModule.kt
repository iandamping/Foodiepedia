package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.cache.preference.listener.BooleanPrefValueListener
import com.ian.junemon.foodiepedia.core.cache.preference.listener.IntPrefValueListener
import com.ian.junemon.foodiepedia.core.cache.preference.listener.StringPrefValueListener
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 24,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object SharedPreferenceListenerModule {

    @JvmStatic
    @Provides
    fun provideStringListener() = StringPrefValueListener()

    @JvmStatic
    @Provides
    fun provideBooleanListener() = BooleanPrefValueListener()

    @JvmStatic
    @Provides
    fun provideIntListener() = IntPrefValueListener()
}