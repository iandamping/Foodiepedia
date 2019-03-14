package com.example.junemon.foodapi_mvvm.model

import com.google.gson.annotations.SerializedName

data class AreaFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(@field:SerializedName("strArea") val strArea: String?)
}