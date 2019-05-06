package com.example.junemon.foodapi_mvvm.util

import android.util.Log
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T> T.logD(msg: String?) {
    val tag = T::class.java.simpleName
    Log.e(tag, msg)
}