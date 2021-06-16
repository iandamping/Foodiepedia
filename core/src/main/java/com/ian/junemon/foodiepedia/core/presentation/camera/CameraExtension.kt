package com.ian.junemon.foodiepedia.core.presentation.camera

import android.view.ViewTreeObserver
import androidx.camera.view.PreviewView

/**
 * Created by Ian Damping on 15,June,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
inline fun PreviewView.afterMeasured(crossinline block: () -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                block()
            }
        }
    })
}