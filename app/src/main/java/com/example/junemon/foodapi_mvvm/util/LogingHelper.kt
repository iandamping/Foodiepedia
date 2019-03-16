package com.example.junemon.foodapi_mvvm.util

import android.util.Log

inline fun <reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    Log.e(tag, msg)
}