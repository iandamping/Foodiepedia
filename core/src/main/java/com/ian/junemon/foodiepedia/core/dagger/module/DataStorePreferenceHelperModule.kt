package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.data.datasource.cache.datastore.DataStoreHelper
import com.ian.junemon.foodiepedia.core.data.datasource.cache.datastore.DataStoreHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class DataStorePreferenceHelperModule {

    @Binds
    abstract fun bindDataStoreHelper(dataStoreHelper: DataStoreHelperImpl): DataStoreHelper
}