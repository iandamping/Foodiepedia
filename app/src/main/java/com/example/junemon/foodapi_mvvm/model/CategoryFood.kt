package com.example.junemon.foodapi_mvvm.model

import com.google.gson.annotations.SerializedName

data class CategoryFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(@field:SerializedName("strCategory") val category: String?)
}