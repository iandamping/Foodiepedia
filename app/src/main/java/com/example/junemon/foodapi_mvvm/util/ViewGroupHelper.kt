package com.example.junemon.foodapi_mvvm.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/*
Created by ian 10/March/2019
 */
fun ViewGroup.inflates(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}