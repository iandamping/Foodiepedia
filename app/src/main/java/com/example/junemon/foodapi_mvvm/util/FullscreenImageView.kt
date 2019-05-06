package com.example.junemon.foodapi_mvvm.util

import android.app.Dialog
import android.content.Context
import android.view.Window
import com.example.junemon.foodapi_mvvm.R
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */
fun Context.fullScreen(imageUrl: String?) {
    imageUrl?.let {
        val alert = Dialog(this, R.style.AppTheme)
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alert.setContentView(R.layout.activity_fullscreen)
        alert.setCanceledOnTouchOutside(true)
        alert.fullScreenImageView.loadUrl(imageUrl)
        alert.show()
        alert.ivClose.setOnClickListener {
            alert.dismiss()
        }
    }

}