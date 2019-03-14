package com.example.junemon.foodapi_mvvm.util

import android.widget.ImageView
import com.squareup.picasso.Picasso

/*
Created by ian 10/March/2019
 */
fun ImageView.loadUrl(url: String?) {
    url?.let { Picasso.get().load(it).into(this) }

}