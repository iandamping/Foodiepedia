package com.ian.junemon.foodiepedia.core.presentation.camera.helper

import androidx.camera.core.Camera
import androidx.camera.core.CameraInfoUnavailableException
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.core.SurfaceOrientedMeteringPointFactory
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxOutputOptions
import com.ian.junemon.foodiepedia.core.dagger.qualifier.LensFacingBack
import com.ian.junemon.foodiepedia.core.dagger.qualifier.LensFacingFront
import com.ian.junemon.foodiepedia.core.presentation.camera.afterMeasured
import com.ian.junemon.foodiepedia.core.presentation.camera.photo.ImageCaptureListener
import timber.log.Timber
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class CameraxHelperImpl @Inject constructor(
    private val cameraMainExecutor: Executor,
    private val cameraFuture: ListenableFuture<ProcessCameraProvider>,
    private val cameraProcessProvider: ProcessCameraProvider,
    @LensFacingBack private val backCameraSelector: CameraSelector,
    @CameraxOutputOptions private val outputOptions: ImageCapture.OutputFileOptions,
    private val imageCaptureListener: ImageCaptureListener,
    private val imageCapture: ImageCapture,
    ) : CameraxHelper {


    override fun startCameraForTakePhoto(
        lifecycleOwner: LifecycleOwner,
        preview: Preview,
        camera: (Camera) -> Unit
    ) {
        cameraFuture.addListener({
            try {
                // Unbind use cases before rebinding
                unbindCamera()
                // Bind use cases to camera
                camera(
                    cameraProcessProvider.bindToLifecycle(
                        lifecycleOwner, backCameraSelector, preview, imageCapture
                    )
                )
            } catch (exc: Exception) {
                Timber.e("Use case binding failed : $exc")
            }

        }, cameraMainExecutor)
    }

    override fun takePhoto() {
        imageCapture.takePicture(
            outputOptions,
            cameraMainExecutor,
            imageCaptureListener.providePhotoListener()
        )
    }


    override fun autoFocusPreview(view: PreviewView, camera: Camera) {
        view.afterMeasured {
            val autoFocusPoint = SurfaceOrientedMeteringPointFactory(1f, 1f)
                .createPoint(.5f, .5f)
            try {
                val autoFocusAction = FocusMeteringAction.Builder(
                    autoFocusPoint,
                    FocusMeteringAction.FLAG_AF
                ).apply {
                    //start auto-focusing after 2 seconds
                    setAutoCancelDuration(2, TimeUnit.SECONDS)
                }.build()
                camera.cameraControl.startFocusAndMetering(autoFocusAction)
            } catch (e: CameraInfoUnavailableException) {
                Timber.e("cannot access camera because $e")
            }
        }
    }

    override fun providePreview(view: PreviewView): Preview {
        return Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(view.surfaceProvider)
            }
    }

    override fun unbindCamera() {
        cameraProcessProvider.unbindAll()
    }

}