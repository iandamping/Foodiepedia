package com.ian.junemon.foodiepedia.feature.di.component

import com.ian.junemon.foodiepedia.core.dagger.scope.FeatureScope
import com.ian.junemon.foodiepedia.dagger.ActivityComponent
import com.ian.junemon.foodiepedia.feature.di.module.FoodModule
import com.ian.junemon.foodiepedia.feature.view.BottomFilterFragment
import com.ian.junemon.foodiepedia.feature.view.DetailFragment
import com.ian.junemon.foodiepedia.feature.view.HomeFragment
import com.ian.junemon.foodiepedia.feature.view.ProfileFragment
import com.ian.junemon.foodiepedia.feature.view.SearchFragment
import com.ian.junemon.foodiepedia.feature.view.UploadFoodFragment
import dagger.Component

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Component(modules = [FoodModule::class], dependencies = [ActivityComponent::class])
@FeatureScope
interface FoodComponent {

    fun inject(fragment: DetailFragment)

    fun inject(fragment: UploadFoodFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: ProfileFragment)

    fun inject(fragment: SearchFragment)

    fun inject(fragment: BottomFilterFragment)

    @Component.Factory
    interface Factory {
        fun create(activityComponent: ActivityComponent): FoodComponent
    }
}

interface FoodComponentProvider {

    fun provideFoodComponent(): FoodComponent
}