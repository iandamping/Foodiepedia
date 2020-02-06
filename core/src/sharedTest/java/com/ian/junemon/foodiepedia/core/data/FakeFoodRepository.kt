package com.ian.junemon.foodiepedia.core.data

import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource

/**
 * Created by Ian Damping on 06,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodRepository(
    private val remoteDataSource: FoodRemoteDataSource,
    private val cacheDataSource: FoodCacheDataSource
) {

}