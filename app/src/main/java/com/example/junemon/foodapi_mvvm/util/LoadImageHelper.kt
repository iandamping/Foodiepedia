package com.example.junemon.foodapi_mvvm.util

import android.widget.ImageView
import com.example.junemon.foodapi_mvvm.R
import com.squareup.picasso.Picasso

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

fun ImageView.loadUrl(url: String?) {
    url?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).into(this) }

}

fun ImageView.loadUrlResize(url: String?) {
    url?.let { Picasso.get().load(it).placeholder(R.drawable.empty_image).resize(100, 100).into(this) }

}