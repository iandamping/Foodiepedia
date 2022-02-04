package com.ian.junemon.foodiepedia.core.presentation.camera.listener

import com.ian.junemon.foodiepedia.core.presentation.camera.ImageCaptureState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PhotoListenerImpl @Inject constructor() : PhotoListener {

    private val _photoState: MutableStateFlow<ImageCaptureState> =
        MutableStateFlow(ImageCaptureState.initialize())

    override fun setPhotoState(data: ImageCaptureState) {
        _photoState.value = data
    }

    override val photoState: Flow<ImageCaptureState>
        get() = _photoState.asStateFlow()
}