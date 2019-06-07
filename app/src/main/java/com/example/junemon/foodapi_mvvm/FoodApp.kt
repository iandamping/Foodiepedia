package com.example.junemon.foodapi_mvvm

import android.app.Application
import com.example.junemon.foodapi_mvvm.di.DatabaseModule.databaseModule
import com.example.junemon.foodapi_mvvm.di.NetworkModule.networkMod
import com.example.junemon.foodapi_mvvm.di.RepositoryModule.allRepoModul
import com.example.junemon.foodapi_mvvm.di.ViewModelModule.allViewmodelModule
import com.google.gson.Gson
import org.koin.android.ext.android.startKoin
import timber.log.Timber

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FoodApp : Application() {
    companion object {
        val gson: Gson = Gson()
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(networkMod, allViewmodelModule, allRepoModul, databaseModule))
        Timber.plant(Timber.DebugTree())
    }
}