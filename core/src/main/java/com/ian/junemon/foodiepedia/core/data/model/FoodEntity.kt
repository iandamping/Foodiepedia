package com.ian.junemon.foodiepedia.core.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class FoodEntity(
    @SerializedName("foodName") var foodName: String?,
    @SerializedName("foodCategory")var foodCategory: String?,
    @SerializedName("foodArea")var foodArea: String?,
    @SerializedName("foodImage")var foodImage: String?,
    @SerializedName("foodContributor")var foodContributor: String?,
    @SerializedName("foodDescription")var foodDescription: String?
){
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )
}