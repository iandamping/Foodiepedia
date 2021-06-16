package com.ian.junemon.foodiepedia.core.presentation.camera.listener

import com.ian.junemon.foodiepedia.core.presentation.camera.ImageCaptureState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 13,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PhotoListenerImpl @Inject constructor(private val scope: CoroutineScope) : PhotoListener {

    private val channel = Channel<ImageCaptureState>(Channel.CONFLATED)

    override fun setPhotoState(data: ImageCaptureState) {
        scope.launch {
            channel.send(data)
        }
    }

    override val photoState: Flow<ImageCaptureState>
        get() = channel.receiveAsFlow()
}