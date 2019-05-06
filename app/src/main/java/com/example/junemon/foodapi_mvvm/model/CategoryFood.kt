package com.example.junemon.foodapi_mvvm.model

import com.google.gson.annotations.SerializedName
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class CategoryFood(@field:SerializedName("meals") val allCategory: List<Meal>?) {
    data class Meal(@field:SerializedName("strCategory") val category: String?)
}