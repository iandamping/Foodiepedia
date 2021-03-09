package com.ian.junemon.foodiepedia.feature.view.home

import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface HomeAdapterModule {
    @Binds
    fun bindsHomeAdapter(fragment: HomeFragment): HomeAdapter.HomeAdapterListener
}