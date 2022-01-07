package com.ian.junemon.foodiepedia.dagger.module

import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.dagger.factory.ViewModelKey
import com.ian.junemon.foodiepedia.dagger.scope.FragmentScoped
import com.ian.junemon.foodiepedia.feature.view.BottomFilterFragment
import com.ian.junemon.foodiepedia.feature.view.DetailFragment
import com.ian.junemon.foodiepedia.feature.view.home.HomeFragment
import com.ian.junemon.foodiepedia.feature.view.ProfileFragment
import com.ian.junemon.foodiepedia.feature.view.favorite.FavoriteAdapterModule
import com.ian.junemon.foodiepedia.feature.view.favorite.FavoriteFragment
import com.ian.junemon.foodiepedia.feature.view.search.SearchFragment
import com.ian.junemon.foodiepedia.feature.view.upload.UploadFoodFragment
import com.ian.junemon.foodiepedia.feature.view.home.HomeAdapterModule
import com.ian.junemon.foodiepedia.feature.view.search.SearchAdapterModule
import com.ian.junemon.foodiepedia.feature.view.upload.OpenCameraFragment
import com.ian.junemon.foodiepedia.feature.view.upload.SelectImageFragment
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.feature.vm.SearchFoodViewModel
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

    @FragmentScoped
    @ContributesAndroidInjector(modules = [SearchAdapterModule::class])
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeBottomFilterFragment(): BottomFilterFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [HomeAdapterModule::class])
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeProfileFragment(): ProfileFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeUploadFoodFragment(): UploadFoodFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeOpenCameraFragment(): OpenCameraFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun contributeSelectImageFragment(): SelectImageFragment

    @FragmentScoped
    @ContributesAndroidInjector(modules = [FavoriteAdapterModule::class])
    abstract fun contributeFavoriteFragment(): FavoriteFragment

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(vm: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FoodViewModel::class)
    abstract fun bindFoodViewModel(vm: FoodViewModel): ViewModel

}