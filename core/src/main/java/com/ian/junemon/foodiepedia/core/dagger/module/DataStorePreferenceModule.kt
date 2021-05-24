package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.preferencesDataStore
import com.ian.junemon.foodiepedia.core.util.DataConstant
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Ian Damping on 23,September,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object DataStorePreferenceModule {

    private val Context.dataStore by preferencesDataStore(
        name = DataConstant.prefHelperInit,
        // Since we're migrating from SharedPreferences, add a migration based on the
        // SharedPreferences name
        produceMigrations = { contexts ->
            listOf(SharedPreferencesMigration(contexts, DataConstant.prefHelperInit))
        }
    )

    @Provides
    @Singleton
    fun provideSharedPreference(context: Context) = context.dataStore
}