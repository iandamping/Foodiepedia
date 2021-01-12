package com.ian.junemon.foodiepedia.uploader.util.interfaces

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ViewHelper {

    fun FragmentActivity.fullScreenAnimation()

    fun FragmentActivity.animationOnly()

    fun View.visible()

    fun View.gone()

    fun ViewGroup.inflates(layout: Int): View
}