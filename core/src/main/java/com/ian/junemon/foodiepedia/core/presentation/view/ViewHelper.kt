package com.ian.junemon.foodiepedia.core.presentation.view

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface ViewHelper {

    fun fullScreenAnimation(activity: FragmentActivity)

    fun animationOnly(activity: FragmentActivity)

    fun visible(view: View)

    fun gone(view: View)

    fun inflates(layout: Int, viewGroup: ViewGroup): View
}