package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object SharedPreferenceModule {
    private const val prefHelperInit = " init preference helper"
    @Provides
    @JvmStatic
    fun provideSharedPreference(context: Context):SharedPreferences{
        return context.getSharedPreferences(prefHelperInit, Context.MODE_PRIVATE)
    }
}