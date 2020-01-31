package com.ian.junemon.foodiepedia.core.worker.creator

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * Created by Ian Damping on 31,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FetcherWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): CoroutineWorker
}