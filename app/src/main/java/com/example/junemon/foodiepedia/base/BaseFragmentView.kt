package com.example.junemon.foodiepedia.base

import android.view.View

/**
 *
Created by Ian Damping on 27/03/2019.
Github = https://github.com/iandamping
 */
interface BaseFragmentView {
    fun initView(view: View)
    fun onFailedGetData(msg: String?)
}