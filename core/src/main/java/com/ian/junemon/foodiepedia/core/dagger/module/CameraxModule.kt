package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.presentation.camera.helper.CameraxHelper
import com.ian.junemon.foodiepedia.core.presentation.camera.helper.CameraxHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Ian Damping on 15,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
@InstallIn(SingletonComponent::class)
interface CameraxModule {
    @Binds
    @Singleton
    fun bindsCameraHelper(helper:CameraxHelperImpl):CameraxHelper
}