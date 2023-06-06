package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.data.datasource.cache.preference.PreferenceHelper
import com.ian.junemon.foodiepedia.core.data.datasource.cache.preference.PreferenceHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PreferenceHelperModule {

    @Binds
    fun bindsPreferenceHelper(impl: PreferenceHelperImpl): PreferenceHelper
}