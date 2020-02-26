package com.ian.junemon.foodiepedia.core.presentation.util.classes

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 01,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

class LoadImageImpl @Inject constructor() :
    LoadImageHelper {
    private val options by lazy { RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE) }

    override fun ImageView.loadWithGlideSmall(url: String?) {
        Glide.with(this.context).load(url).apply(options.override(150, 150))
            .placeholder(R.drawable.empty_image).thumbnail(0.25f)
            .error(R.drawable.no_data)
            .into(this)
    }

    override fun ImageView.loadWithGlideCustomSize(url: String?, width: Int, height: Int) {
        Glide.with(this.context).load(url).apply(options.override(width, height))
            .error(R.drawable.no_data)
            .placeholder(R.drawable.empty_image).thumbnail(0.25f)
            .into(this)
    }

    override fun ImageView.loadWithGlide(url: String?) {
        Glide.with(this.context).load(url).apply(options).placeholder(R.drawable.empty_image)
            .error(R.drawable.no_data)
            .thumbnail(0.25f).into(this)
    }

    override fun ImageView.loadWithGlide(drawable: Drawable) {
        Glide.with(this.context).load(drawable).into(this)
    }

    override fun ImageView.loadWithGlide(bitmap: Bitmap) {
        Glide.with(this.context).load(drawable).into(this)
    }
}