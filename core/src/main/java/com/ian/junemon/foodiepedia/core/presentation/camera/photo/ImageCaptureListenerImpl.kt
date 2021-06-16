package com.ian.junemon.foodiepedia.core.presentation.camera.photo

import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import com.ian.junemon.foodiepedia.core.presentation.camera.ImageCaptureState
import com.ian.junemon.foodiepedia.core.presentation.camera.listener.PhotoListener
import javax.inject.Inject

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ImageCaptureListenerImpl @Inject constructor(
    private val listener: PhotoListener
) :
    ImageCaptureListener {

    override fun providePhotoListener(): ImageCapture.OnImageSavedCallback {
       return object : ImageCapture.OnImageSavedCallback {
           override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
               listener.setPhotoState(ImageCaptureState.Success)
           }

           override fun onError(exception: ImageCaptureException) {
               listener.setPhotoState(ImageCaptureState.Failed("Photo capture failed: ${exception.message}"))
           }
       }
    }
}