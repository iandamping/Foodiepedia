package com.ian.junemon.foodiepedia.core.worker

import android.content.Context
import android.os.Build
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

/**
 * Created by Ian Damping on 31,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun setupReccuringWork(applicationContext: Context) {

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .setRequiresBatteryNotLow(true)
        .setRequiresCharging(true)
        .apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                setRequiresDeviceIdle(true)
            }
        }.build()

    val repeatingRequest =
        PeriodicWorkRequestBuilder<DataFetcherWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

    WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
        DataFetcherWorker.WORK_NAME,
        ExistingPeriodicWorkPolicy.KEEP,
        repeatingRequest)
}