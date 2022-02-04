package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.data.datasource.cache.datastore.DataStoreHelper
import com.ian.junemon.foodiepedia.core.data.datasource.cache.datastore.DataStoreHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataStorePreferenceHelperModule {

    @Binds
    fun bindDataStoreHelper(dataStoreHelper: DataStoreHelperImpl): DataStoreHelper
}