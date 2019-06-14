package com.ian.junemon.foodiepedia.base

import android.view.View

interface BaseFragmentPresenterHelper {
    fun onAttach()
    fun onCreateView(view: View)
}