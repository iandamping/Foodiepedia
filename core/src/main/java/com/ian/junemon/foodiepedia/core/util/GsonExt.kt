package com.ian.junemon.foodiepedia.core.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


inline fun <reified T> Gson.fromJsonHelper(data: String): T =
    this.fromJson<T>(data, object : TypeToken<T>() {}.type)