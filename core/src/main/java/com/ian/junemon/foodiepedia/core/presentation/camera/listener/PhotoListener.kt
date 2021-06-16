package com.ian.junemon.foodiepedia.core.presentation.camera.listener

import com.ian.junemon.foodiepedia.core.presentation.camera.ImageCaptureState
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PhotoListener {

    fun setPhotoState(data: ImageCaptureState)

    val photoState: Flow<ImageCaptureState>
}