package com.ian.junemon.foodiepedia.dagger.module

import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelKey
import com.ian.junemon.foodiepedia.core.dagger.scope.FeatureScope
import com.ian.junemon.foodiepedia.feature.view.BottomFilterFragment
import com.ian.junemon.foodiepedia.feature.view.DetailFragment
import com.ian.junemon.foodiepedia.feature.view.HomeFragment
import com.ian.junemon.foodiepedia.feature.view.ProfileFragment
import com.ian.junemon.foodiepedia.feature.view.SearchFragment
import com.ian.junemon.foodiepedia.feature.view.UploadFoodFragment
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class FoodModule {

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeDetailFragment(): DetailFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeBottomFilterFragment(): BottomFilterFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment

    @FeatureScope
    @ContributesAndroidInjector
    abstract fun contributeUploadFoodFragment(): UploadFoodFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(gameViewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FoodViewModel::class)
    abstract fun bindFoodViewModel(gameViewModel: FoodViewModel): ViewModel
}