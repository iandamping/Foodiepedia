package com.example.junemon.foodapi_mvvm.util

import android.content.res.Resources

fun Resources.getColors(id: Int): Int {
    return this.getColor(id)
}

fun Resources.getArrayString(id: Int): List<String> {
    return this.getStringArray(id).toList()
}