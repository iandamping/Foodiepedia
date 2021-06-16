package com.ian.junemon.foodiepedia.core.presentation.camera.helper

import androidx.camera.core.Camera
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Ian Damping on 29,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface CameraxHelper {

    fun startCameraForTakePhoto(lifecycleOwner: LifecycleOwner,preview: Preview, camera: (Camera)->Unit)

    fun takePhoto()

    fun providePreview(view: PreviewView): Preview

    fun autoFocusPreview(view: PreviewView,camera: Camera)

    fun unbindCamera()
}