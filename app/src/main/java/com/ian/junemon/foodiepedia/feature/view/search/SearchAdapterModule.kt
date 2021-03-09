package com.ian.junemon.foodiepedia.feature.view.search

import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface SearchAdapterModule {
    @Binds
    fun bindsSearchAdapter(fragment: SearchFragment): SearchAdapter.SearchAdapterListener
}