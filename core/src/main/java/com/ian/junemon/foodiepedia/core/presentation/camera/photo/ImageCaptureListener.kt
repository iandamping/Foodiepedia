package com.ian.junemon.foodiepedia.core.presentation.camera.photo

import androidx.camera.core.ImageCapture

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ImageCaptureListener {

    fun providePhotoListener(): ImageCapture.OnImageSavedCallback
}