package com.ian.junemon.foodiepedia.food.di

import com.ian.junemon.foodiepedia.core.dagger.scope.FeatureScope
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.food.view.DetailFragment
import com.ian.junemon.foodiepedia.food.view.HomeFragment
import dagger.Component

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(dependencies = [ActivityComponent::class])
@FeatureScope
interface FoodComponent {

    fun inject(fragment: DetailFragment)

    fun inject(fragment: HomeFragment)

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): FoodComponent
    }
}