package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.filter.LocalFilterData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class FilterFood(@field:SerializedName("meals") val allFilter: List<Meal>?) {
    data class Meal(
            @field:SerializedName("idMeal") val idMeal: String?,
            @field:SerializedName("strMeal") val strMeal: String?,
            @field:SerializedName("strMealThumb") val strMealThumb: String
    )
}

fun List<FilterFood.Meal>.toDatabaseModel(): MutableList<LocalFilterData> {
    return this.map { nonNullData ->
        LocalFilterData(nonNullData.idMeal!!, nonNullData.strMeal, nonNullData.strMealThumb)
    }.toMutableList()
}