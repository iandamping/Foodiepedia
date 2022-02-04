package com.ian.junemon.foodiepedia.core.presentation.view

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ian.junemon.foodiepedia.core.dagger.qualifier.IoDispatcher
import com.ian.junemon.foodiepedia.core.dagger.qualifier.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

class LoadImageImpl @Inject constructor(
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val glideInstance: Glide,
    private val requestManager: RequestManager,
    private val requestOptions: RequestOptions
) :
    LoadImageHelper {

    override fun loadWithGlideSmall(view: ImageView, url: String?) {
        requestManager.load(url).apply(requestOptions.override(150, 150))
            .thumbnail(0.25f)
            .into(view)
    }

    override fun loadWithGlideCustomSize(view: ImageView, url: String?, width: Int, height: Int) {
        requestManager.load(url).apply(requestOptions.override(width, height))
            .thumbnail(0.25f)
            .into(view)
    }

    override fun loadWithGlide(view: ImageView, url: String?) {
        requestManager.load(url).apply(requestOptions)
            .thumbnail(0.25f).into(view)
    }

    override fun loadWithGlide(view: ImageView, drawable: Drawable) {
        requestManager.load(drawable).into(view)
    }

    override fun loadWithGlide(view: ImageView, bitmap: Bitmap?) {
        requestManager.load(bitmap).into(view)
    }

    override fun loadWithGlide(view: ImageView, file: File) {
        requestManager.load(file).into(view)
    }

    override suspend fun clearMemory() = withContext(mainDispatcher) {
        glideInstance.clearMemory()
    }

    override suspend fun clearDiskCache() = withContext(ioDispatcher) {
        glideInstance.clearDiskCache()
    }
}