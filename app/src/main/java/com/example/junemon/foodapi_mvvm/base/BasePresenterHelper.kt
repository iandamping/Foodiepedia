package com.example.junemon.foodapi_mvvm.base

import androidx.fragment.app.FragmentActivity

interface BasePresenterHelper {
    fun onCreate(lifeCycleOwner: FragmentActivity)
}