package com.ian.junemon.foodiepedia.core.dagger.module

import com.ian.junemon.foodiepedia.core.dagger.qualifier.DefaultDispatcher
import com.ian.junemon.foodiepedia.core.dagger.qualifier.IoDispatcher
import com.ian.junemon.foodiepedia.core.dagger.qualifier.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Created by Ian Damping on 08,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CoroutineModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}