package com.ian.junemon.foodiepedia.core.remote.di

import com.ian.junemon.foodiepedia.core.remote.util.FoodRemoteHelper
import com.ian.junemon.foodiepedia.core.remote.util.FoodRemoteHelperImpl
import com.ian.junemon.foodiepedia.core.remote.util.ProfileRemoteHelper
import com.ian.junemon.foodiepedia.core.remote.util.ProfileRemoteHelperImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class RemoteHelperModule {

    @Binds
    abstract fun bindsFoodRemoteHelper(foodRemoteHelper: FoodRemoteHelperImpl): FoodRemoteHelper

    @Binds
    abstract fun bindsProfileRemoteHelper(profileRemoteHelper: ProfileRemoteHelperImpl): ProfileRemoteHelper
}