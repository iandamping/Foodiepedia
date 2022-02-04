package com.ian.junemon.foodiepedia.core.presentation.camera

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

data class ImageCaptureState(
    val success:String,
    val failed:String
){
    companion object {
        fun initialize() = ImageCaptureState(
            success = "",
            failed = ""
        )
    }
}
