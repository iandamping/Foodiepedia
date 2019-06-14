package com.ian.junemon.foodiepedia

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.google.android.gms.ads.MobileAds
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.di.DatabaseModule.databaseModule
import com.ian.junemon.foodiepedia.di.NetworkModule.networkMod
import com.ian.junemon.foodiepedia.di.RepositoryModule.allRepoModul
import com.ian.junemon.foodiepedia.di.ViewModelModule.allViewmodelModule
import com.ian.junemon.foodiepedia.util.PreferenceHelper
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.startKoin

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FoodApp : Application() {

    companion object {
        val gson: Gson = Gson()
        lateinit var prefHelper: PreferenceHelper
        lateinit var mFirebaseAuth: FirebaseAuth

        init {
            System.loadLibrary("ian")
        }

    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        MobileAds.initialize(this, getString(R.string.admobID_banner_test))
        mFirebaseAuth = FirebaseAuth.getInstance()
        prefHelper = PreferenceHelper(this)
        startKoin(this, listOf(networkMod, allViewmodelModule, allRepoModul, databaseModule))
    }
}