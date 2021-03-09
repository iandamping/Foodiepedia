package com.ian.junemon.foodiepedia.dagger

import com.ian.junemon.foodiepedia.FoodApp
import com.ian.junemon.foodiepedia.core.dagger.module.DatabaseModule
import com.ian.junemon.foodiepedia.core.dagger.module.GlideModule
import com.ian.junemon.foodiepedia.core.dagger.module.CoroutineModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataModule
import com.ian.junemon.foodiepedia.core.dagger.module.DomainModule
import com.ian.junemon.foodiepedia.dagger.module.PresentationModule
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataStorePreferenceHelperModule
import com.ian.junemon.foodiepedia.core.dagger.module.RemoteModule
import com.ian.junemon.foodiepedia.core.dagger.module.DataStorePreferenceModule
import com.ian.junemon.foodiepedia.dagger.module.ActivityBindingModule
import com.ian.junemon.foodiepedia.dagger.module.AppModule
import com.ian.junemon.foodiepedia.dagger.module.CameraXModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

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
        PresentationModule::class,
        CameraXModule::class,
        GlideModule::class]
)
interface AppComponent : AndroidInjector<FoodApp> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<FoodApp>
}