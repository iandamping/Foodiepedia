package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.data.repository.FoodRepositoryImpl
import com.ian.junemon.foodiepedia.core.data.data.repository.ProfileRepositoryImpl
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.cache.ProfileCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.ProfileRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class DataModule {

    @Binds
    abstract fun bindFoodRemoteDataSource(remoteDataSource: FoodRemoteDataSourceImpl): FoodRemoteDataSource

    @Binds
    abstract fun bindFoodCacheDataSource(cacheDataSource: FoodCacheDataSourceImpl): FoodCacheDataSource

    @Binds
    abstract fun bindProfileRemoteDataSource(remoteDataSource: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource

    @Binds
    abstract fun bindProfileCacheDataSource(remoteDataSource: ProfileCacheDataSourceImpl): ProfileCacheDataSource

    @Binds
    abstract fun bindProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    abstract fun bindFoodRepository(foodRepository: FoodRepositoryImpl): FoodRepository
}