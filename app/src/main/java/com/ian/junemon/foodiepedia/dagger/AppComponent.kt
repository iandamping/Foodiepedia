package com.ian.junemon.foodiepedia.dagger

import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponent
import com.ian.junemon.foodiepedia.core.dagger.scope.ApplicationScope
import dagger.Component

@ApplicationScope
@Component(dependencies = [CoreComponent::class])
interface AppComponent {

   /* fun provideLoadImageHelper(): LoadImageHelper

    fun provideIntentUtil(): IntentHelper

    fun provideImageUtil(): ImageHelper

    fun providePermissionHelperUtil(): PermissionHelper

    fun provideRecyclerViewHelper(): RecyclerHelper

    fun provideViewHelper(): ViewHelper

    fun provideCommmonHelper(): CommonHelper

    fun providePlaceRepository(): PlaceRepository

    fun providePlaceRemoteDataSource(): PlaceRemoteDataSource

    fun providePlaceCacheDataSource(): PlaceCacheDataSource

    fun providePlacesDaoHelper(): PlacesDaoHelper

    fun provideRemoteHelper(): RemoteHelper*/

    @Component.Factory
    interface Factory {
        fun coreComponent(coreComponent: CoreComponent): AppComponent
    }
}