package com.ian.junemon.foodiepedia.core.worker.di

import androidx.work.WorkerFactory
import com.ian.junemon.foodiepedia.core.worker.DataFetcherWorker
import com.ian.junemon.foodiepedia.core.worker.creator.FetcherWorkerFactory
import com.ian.junemon.foodiepedia.core.worker.creator.FetcherWorkerFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Ian Damping on 31,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
abstract class WorkerModule {

    @Binds
    @IntoMap
    @WorkerKey(DataFetcherWorker::class)
    abstract fun bindDataFetcherWorker(factory: DataFetcherWorker.Factory): FetcherWorkerFactory

}