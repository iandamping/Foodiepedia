package com.ian.junemon.foodiepedia.core.dagger.factory

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by Ian Damping on 16,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory):ViewModelProvider.Factory
}