package com.example.junemon.foodapi_mvvm.util

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.example.junemon.foodapi_mvvm.FoodApp.Companion.gson

inline fun <reified T : Activity> FragmentActivity.startActivity() {
    startActivity(Intent(this, T::class.java))
}

inline fun <reified T : Activity> FragmentActivity.startActivityWithString(key: String?, value: String?) {
    startActivity(Intent(this, T::class.java).putExtra(key, value))
}

inline fun <reified T : Activity> FragmentActivity.startActivityWithData(key: String?, value: Any?) {
    startActivity(Intent(this, T::class.java).putExtra(key, gson.toJson(value)))
}
