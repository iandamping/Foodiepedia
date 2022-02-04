package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.repository.FoodRepositoryImpl
import com.ian.junemon.foodiepedia.core.data.repository.ProfileRepositoryImpl
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.cache.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.ProfileCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FoodRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.ProfileRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindFoodRemoteDataSource(remoteDataSource: FoodRemoteDataSourceImpl): FoodRemoteDataSource

    @Binds
    @Singleton
    fun bindFoodCacheDataSource(cacheDataSource: FoodCacheDataSourceImpl): FoodCacheDataSource

    @Binds
    @Singleton
    fun bindProfileRemoteDataSource(remoteDataSource: ProfileRemoteDataSourceImpl): ProfileRemoteDataSource

    @Binds
    @Singleton
    fun bindProfileCacheDataSource(remoteDataSource: ProfileCacheDataSourceImpl): ProfileCacheDataSource

    @Binds
    @Singleton
    fun bindProfileRepository(profileRepository: ProfileRepositoryImpl): ProfileRepository

    @Binds
    @Singleton
    fun bindFoodRepository(foodRepository: FoodRepositoryImpl): FoodRepository
}