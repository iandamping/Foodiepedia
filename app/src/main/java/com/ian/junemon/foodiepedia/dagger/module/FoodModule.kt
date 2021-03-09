package com.ian.junemon.foodiepedia.dagger.module

import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelKey
import com.ian.junemon.foodiepedia.dagger.scoped.FragmentScoped
import com.ian.junemon.foodiepedia.feature.view.BottomFilterFragment
import com.ian.junemon.foodiepedia.feature.view.DetailFragment
import com.ian.junemon.foodiepedia.feature.view.home.HomeFragment
import com.ian.junemon.foodiepedia.feature.view.ProfileFragment
import com.ian.junemon.foodiepedia.feature.view.search.SearchFragment
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

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeBottomFilterFragment(): BottomFilterFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    @FragmentScoped
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