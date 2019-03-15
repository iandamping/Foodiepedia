package com.example.junemon.foodapi_mvvm.model

import com.google.gson.annotations.SerializedName

data class IngredientFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(
            @field:SerializedName("idIngredient") val id: String?,
            @field:SerializedName("strIngredient") val strIngredient: String?,
            @field:SerializedName("strDescription") val strDescription: String?,
            @field:SerializedName("strType") val strType: String?
    )
}