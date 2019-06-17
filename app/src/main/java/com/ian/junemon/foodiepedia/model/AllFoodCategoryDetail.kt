package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class AllFoodCategoryDetail(@field:SerializedName("categories") val list: List<Category>?) {
    data class Category(
            @field:SerializedName("idCategory") val idCategory: String?,
            @field:SerializedName("strCategory") val strCategory: String?,
            @field:SerializedName("strCategoryThumb") val strCategoryThumb: String?,
            @field:SerializedName("strCategoryDescription") val strCategoryDescription: String?
    )
}

fun List<AllFoodCategoryDetail.Category>.toDatabaseModel(): MutableList<LocalAllFoodCategoryDetailData> {
    return this.map { nonNullData ->
        LocalAllFoodCategoryDetailData( nonNullData.idCategory!!, nonNullData.strCategory, nonNullData.strCategoryThumb, nonNullData.strCategoryDescription)
    }.toMutableList()
}