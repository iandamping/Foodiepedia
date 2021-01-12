package com.ian.junemon.foodiepedia.uploader.dagger

import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelModule
import com.ian.junemon.foodiepedia.core.dagger.module.CoroutineModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataStorePreferenceHelperModule
import com.ian.junemon.foodiepedia.core.dagger.module.DatabaseModule
import com.ian.junemon.foodiepedia.core.dagger.module.DomainModule
import com.ian.junemon.foodiepedia.core.dagger.module.GlideModule
import com.ian.junemon.foodiepedia.core.dagger.module.RemoteModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataStorePreferenceModule
import com.ian.junemon.foodiepedia.uploader.UploadApplication
import com.ian.junemon.foodiepedia.uploader.dagger.module.ActivityBindingModule
import com.ian.junemon.foodiepedia.uploader.dagger.module.AppModule
import com.ian.junemon.foodiepedia.uploader.dagger.module.UploadPresentationModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Ian Damping on 26,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        DatabaseModule::class,
        DataStorePreferenceModule::class,
        DataStorePreferenceHelperModule::class,
        CoroutineModule::class,
        DataModule::class,
        DomainModule::class,
        RemoteModule::class,
        UploadPresentationModule::class,
        GlideModule::class]
)
interface UploadComponent : AndroidInjector<UploadApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<UploadApplication>()
}