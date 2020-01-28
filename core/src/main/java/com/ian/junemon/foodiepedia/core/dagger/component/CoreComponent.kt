package com.ian.junemon.foodiepedia.core.dagger.component

import android.app.Application
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.cache.di.DatabaseHelperModule
import com.ian.junemon.foodiepedia.core.cache.di.DatabaseModule
import com.ian.junemon.foodiepedia.core.data.di.CoroutineModule
import com.ian.junemon.foodiepedia.core.data.di.DataModule
import com.ian.junemon.foodiepedia.core.domain.di.DomainModule
import com.ian.junemon.foodiepedia.core.presentation.di.PresentationModule
import com.ian.junemon.foodiepedia.core.remote.di.RemoteHelperModule
import com.ian.junemon.foodiepedia.core.remote.di.RemoteModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Component(
    modules = [DatabaseModule::class, DatabaseHelperModule::class, DataModule::class, DomainModule::class,
        RemoteModule ::class, RemoteHelperModule::class, CoroutineModule::class]
)
@Singleton
interface CoreComponent {

    val provideStorageReference:StorageReference

    /* val providePlacesDaoHelper: FoodDaoHelper

     val provideFoodDaoHelper: ProfileDaoHelper*/

    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: Application): CoreComponent
    }
}