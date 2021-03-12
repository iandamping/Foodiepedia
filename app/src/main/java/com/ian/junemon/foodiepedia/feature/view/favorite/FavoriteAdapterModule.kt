package com.ian.junemon.foodiepedia.feature.view.favorite

import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 11,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
interface FavoriteAdapterModule {

    @Binds
    fun bindFavoriteAdapter(fragment: FavoriteFragment):FavoriteAdapter.FavoriteAdapterListener
}