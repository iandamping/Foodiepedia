package com.ian.junemon.foodiepedia.core.dagger.component

import android.app.Application
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.cache.di.DatabaseHelperModule
import com.ian.junemon.foodiepedia.core.cache.di.DatabaseModule
import com.ian.junemon.foodiepedia.core.data.di.CoroutineModule
import com.ian.junemon.foodiepedia.core.data.di.DataModule
import com.ian.junemon.foodiepedia.core.domain.di.DomainModule
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.remote.di.RemoteHelperModule
import com.ian.junemon.foodiepedia.core.remote.di.RemoteModule
import com.ian.junemon.foodiepedia.core.worker.di.WorkerModule
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
        RemoteModule::class, RemoteHelperModule::class, WorkerModule::class, CoroutineModule::class]
)
@Singleton
interface CoreComponent {

    val provideStorageReference: StorageReference

    val provideFoodRepository: FoodRepository

    val provideProfileRepository: ProfileRepository

    /*val provideworkerFactoryImpl: FetcherWorkerFactoryImpl*/

    @Component.Factory
    interface Factory {
        fun injectApplication(@BindsInstance application: Application): CoreComponent
    }
}