package com.ian.junemon.foodiepedia.food.di.component

import com.ian.junemon.foodiepedia.core.dagger.scope.FeatureScope
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.food.di.module.ProfileModule
import com.ian.junemon.foodiepedia.food.view.ProfileFragment
import dagger.Component

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(modules = [ProfileModule::class], dependencies = [ActivityComponent::class])
@FeatureScope
interface ProfileComponent {

    fun inject(fragment: ProfileFragment)

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): ProfileComponent
    }
}