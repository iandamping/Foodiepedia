package com.ian.junemon.foodiepedia.dagger

import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.dagger.component.CoreComponent
import com.ian.junemon.foodiepedia.core.dagger.scope.ApplicationScope
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import dagger.Component

@ApplicationScope
@Component(dependencies = [CoreComponent::class])
interface AppComponent {

    val provideStorageReference: StorageReference

    val provideFoodRepository: FoodRepository

    val provideProfileRepository: ProfileRepository

    @Component.Factory
    interface Factory {
        fun coreComponent(coreComponent: CoreComponent): AppComponent
    }
}