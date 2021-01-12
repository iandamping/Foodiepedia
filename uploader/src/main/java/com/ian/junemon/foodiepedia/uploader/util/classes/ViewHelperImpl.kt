package com.ian.junemon.foodiepedia.uploader.util.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ViewHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ViewHelperImpl @Inject constructor() : ViewHelper {

    override fun FragmentActivity.fullScreenAnimation() {
        this.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun FragmentActivity.animationOnly() {
        this.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
    }

    override fun View.visible() {
        this.visibility = View.VISIBLE
    }

    override fun View.gone() {
        this.visibility = View.GONE
    }

    override fun ViewGroup.inflates(layout: Int): View {
        return LayoutInflater.from(context).inflate(layout, this, false)
    }
}