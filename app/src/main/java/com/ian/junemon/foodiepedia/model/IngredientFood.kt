package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.ingredient.LocalIngredientData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class IngredientFood(@field:SerializedName("meals") val allIngredientData: List<Meal>?) {
    data class Meal(
            @field:SerializedName("idIngredient") val id: String?,
            @field:SerializedName("strIngredient") val strIngredient: String?,
            @field:SerializedName("strDescription") val strDescription: String?,
            @field:SerializedName("strType") val strType: String?
    )
}

fun List<IngredientFood.Meal>.toDatabaseModel(): MutableList<LocalIngredientData> {
    return this.map { nonNullData ->
        LocalIngredientData(nonNullData.id!!, nonNullData.strIngredient, nonNullData.strDescription, nonNullData.strType)
    }.toMutableList()
}