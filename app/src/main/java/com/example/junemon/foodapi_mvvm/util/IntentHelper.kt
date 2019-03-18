package com.example.junemon.foodapi_mvvm.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.junemon.foodapi_mvvm.FoodApp.Companion.gson

inline fun <reified T : Activity> FragmentActivity.startActivity(
        options: Bundle? = null, noinline init: Intent.() -> Unit = {}) {
    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

//inline fun <reified T : Activity> FragmentActivity.startActivityWithString(key: String?, value: String?) {
//    startActivity(Intent(this, T::class.java).putExtra(key, value))
//}

inline fun <reified T : Activity> FragmentActivity.startActivityWithData(key: String?, value: Any?) {
    startActivity(Intent(this, T::class.java).putExtra(key, gson.toJson(value)))
}

inline fun <reified T : Any> newIntent(ctx: Context): Intent = Intent(ctx, T::class.java)
