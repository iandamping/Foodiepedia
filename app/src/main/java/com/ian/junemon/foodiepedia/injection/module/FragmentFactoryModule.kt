package com.ian.junemon.foodiepedia.injection.module

import androidx.fragment.app.Fragment
import com.ian.junemon.foodiepedia.feature.view.BottomFilterFragment
import com.ian.junemon.foodiepedia.feature.view.DetailFragment
import com.ian.junemon.foodiepedia.feature.view.ProfileFragment
import com.ian.junemon.foodiepedia.feature.view.favorite.FavoriteFragment
import com.ian.junemon.foodiepedia.feature.view.home.HomeFragment
import com.ian.junemon.foodiepedia.feature.view.search.SearchFragment
import com.ian.junemon.foodiepedia.feature.view.upload.OpenCameraFragment
import com.ian.junemon.foodiepedia.feature.view.upload.SelectImageFragment
import com.ian.junemon.foodiepedia.feature.view.upload.UploadFoodFragment
import com.ian.junemon.foodiepedia.injection.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 17,April,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface FragmentFactoryModule {

    @Binds
    @IntoMap
    @FragmentKey(ProfileFragment::class)
    fun bindProfileFragment(fragment: ProfileFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(DetailFragment::class)
    fun bindDetailFragment(fragment: DetailFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(BottomFilterFragment::class)
    fun bindBottomFilterFragment(fragment: BottomFilterFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(OpenCameraFragment::class)
    fun bindOpenCameraFragment(fragment: OpenCameraFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SelectImageFragment::class)
    fun bindSelectImageFragment(fragment: SelectImageFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(UploadFoodFragment::class)
    fun bindUploadFoodFragment(fragment: UploadFoodFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SearchFragment::class)
    fun bindSearchFragment(fragment: SearchFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    fun bindHomeFragment(fragment: HomeFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(FavoriteFragment::class)
    fun bindFavoriteFragment(fragment: FavoriteFragment): Fragment
}