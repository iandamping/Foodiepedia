package com.ian.junemon.foodiepedia.core.presentation.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import java.io.File

/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface LoadImageHelper {
    fun loadWithGlideSmall(view: ImageView, url: String?)
    fun loadWithGlideCustomSize(view: ImageView, url: String?, width: Int, height: Int)
    fun loadWithGlide(view: ImageView, url: String?)
    fun loadWithGlide(view: ImageView, drawable: Drawable)
    fun loadWithGlide(view: ImageView, bitmap: Bitmap?)
    fun loadWithGlide(view: ImageView, file: File)
    suspend fun clearMemory()
    suspend fun clearDiskCache()
}
