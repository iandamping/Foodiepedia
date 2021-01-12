package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.createDataStore
import com.ian.junemon.foodiepedia.core.util.DataConstant
import dagger.Module
import dagger.Provides

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DataStorePreferenceModule {

    @Provides
    fun provideSharedPreference(context: Context) = context.createDataStore(
        name = DataConstant.prefHelperInit,
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        migrations = listOf(SharedPreferencesMigration(context, DataConstant.prefHelperInit))
    )
}