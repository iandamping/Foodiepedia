package com.ian.junemon.foodiepedia.core.presentation.util.classes

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.dagger.module.IoDispatcher
import com.ian.junemon.foodiepedia.core.dagger.module.MainDispatcher
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

class LoadImageImpl @Inject constructor(  @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val glideInstance:Glide,
    private val requestManager: RequestManager,
    private val requestOptions: RequestOptions) :
    LoadImageHelper {

    private val options by lazy { RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) }

    override fun ImageView.loadWithGlideSmall(url: String?) {
        requestManager.load(url).apply(requestOptions.override(150, 150))
            .thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlideCustomSize(url: String?, width: Int, height: Int) {
        requestManager.load(url).apply(requestOptions.override(width, height))
            .thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlide(url: String?) {
        requestManager.load(url).apply(requestOptions)
            .thumbnail(0.25f).into(this)
    }

    override fun ImageView.loadWithGlide(drawable: Drawable) {
        requestManager.load(drawable).into(this)
    }

    override fun ImageView.loadWithGlide(bitmap: Bitmap) {
        requestManager.load(bitmap).into(this)
    }

    override fun ImageView.loadWithGlide(file: File) {
        requestManager.load(file).into(this)
    }

    override suspend fun clearMemory() = withContext(mainDispatcher){
        glideInstance.clearMemory()
    }

    override suspend fun clearDiskCache()  = withContext(ioDispatcher){
        glideInstance.clearDiskCache()
    }
}