package com.ian.junemon.foodiepedia.core.presentation.camera

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
sealed class ImageCaptureState{
    object Success:ImageCaptureState()
    data class Failed(val message:String):ImageCaptureState()
}
