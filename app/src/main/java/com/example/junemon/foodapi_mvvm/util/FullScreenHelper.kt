package com.example.junemon.foodapi_mvvm.util

import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.example.junemon.foodapi_mvvm.R
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

fun FragmentActivity.fullScreenAnimation() {
    this.overridePendingTransition(R.anim.fade_in_activity, R.anim.fade_out_activity)
    this.requestWindowFeature(Window.FEATURE_NO_TITLE)
    this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
    )
}