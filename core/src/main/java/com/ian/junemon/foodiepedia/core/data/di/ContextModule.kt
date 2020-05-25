package com.ian.junemon.foodiepedia.core.data.di

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module


/**
 * Created by Ian Damping on 21,May,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class ContextModule {

    @Binds
    abstract fun bindContext(applicationInstance: Application): Context
}