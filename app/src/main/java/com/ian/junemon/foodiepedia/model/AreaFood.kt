package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.area.LocalAreaData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class AreaFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(@field:SerializedName("strArea") val strArea: String?)
}

fun List<AreaFood.Meal>.toDatabaseModel(): MutableList<LocalAreaData> {
    return this.map { nonNullData ->
        LocalAreaData( nonNullData.strArea!!)
    }.toMutableList()
}