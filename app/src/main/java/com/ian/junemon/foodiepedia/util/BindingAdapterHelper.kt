package com.ian.junemon.foodiepedia.util

import android.widget.ImageButton
import androidx.databinding.BindingAdapter
import com.ian.junemon.foodiepedia.R

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

@BindingAdapter("initBookmark")
fun initBookmarkHelper(view: ImageButton, state: Boolean) {
    if (state) {
        view.setImageResource(R.drawable.ic_bookmarked)
    } else view.setImageResource(R.drawable.ic_unbookmark)
}