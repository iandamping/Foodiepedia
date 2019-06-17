package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.category.LocalCategoryData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class CategoryFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(@field:SerializedName("strCategory") val category: String?)
}

fun List<CategoryFood.Meal>.toDatabaseModel(): MutableList<LocalCategoryData> {
    return this.map { nonNullData ->
        LocalCategoryData(nonNullData.category!!)
    }.toMutableList()
}