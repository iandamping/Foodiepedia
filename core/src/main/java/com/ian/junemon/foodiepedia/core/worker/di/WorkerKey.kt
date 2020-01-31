package com.ian.junemon.foodiepedia.core.worker.di

import androidx.work.CoroutineWorker
import dagger.MapKey
import dagger.Module
import kotlin.reflect.KClass

/**
 * Created by Ian Damping on 31,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out CoroutineWorker>)