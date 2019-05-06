package com.example.junemon.foodapi_mvvm.model

import com.google.gson.annotations.SerializedName
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