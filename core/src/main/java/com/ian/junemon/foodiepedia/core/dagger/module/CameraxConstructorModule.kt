package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.ian.junemon.foodiepedia.core.dagger.qualifier.LensFacingBack
import com.ian.junemon.foodiepedia.core.dagger.qualifier.LensFacingFront
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Singleton

/**
 * Created by Ian Damping on 29,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CameraxConstructorModule {

    @Provides
    @Singleton
    fun provideCameraXExecutor(): ExecutorService = Executors.newSingleThreadExecutor()

    @Provides
    @Singleton
    fun provideCameraMainExecutor(context: Context): Executor =
        ContextCompat.getMainExecutor(context)

    @Provides
    fun provideProcessCameraProviderFuture(context: Context): ListenableFuture<ProcessCameraProvider> =
        ProcessCameraProvider.getInstance(context)

    @Provides
    fun provideProcessCameraProvider(future: ListenableFuture<ProcessCameraProvider>): ProcessCameraProvider =
        future.get()

    @Provides
    @LensFacingBack
    fun provideBackCameraSelector(): CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    @Provides
    @LensFacingFront
    fun provideFrontCameraSelector(): CameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    @Provides
    fun provideImageCapture(): ImageCapture = ImageCapture.Builder()
        .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
        .build()
}