package com.ian.junemon.foodiepedia.model

import com.google.gson.annotations.SerializedName
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

data class AllFood(@field:SerializedName("meals") val food: List<Meal>?) {
    data class Meal(
            @field:SerializedName("idMeal") val idMeal: String?,
            @field:SerializedName("strMeal") val strMeal: String?,
            @field:SerializedName("strDrinkAlternate") val strDrinkAlternate: String?,
            @field:SerializedName("strCategory") val strCategory: String?,
            @field:SerializedName("strArea") val strArea: String?,
            @field:SerializedName("strInstructions") val strInstructions: String?,
            @field:SerializedName("strMealThumb") val strMealThumb: String?,
            @field:SerializedName("strTags") val strTags: String?,
            @field:SerializedName("strYoutube") val strYoutube: String?,
            @field:SerializedName("strIngredient1") val strIngredient1: String?,
            @field:SerializedName("strIngredient2") val strIngredient2: String?,
            @field:SerializedName("strIngredient3") val strIngredient3: String?,
            @field:SerializedName("strIngredient4") val strIngredient4: String?,
            @field:SerializedName("strIngredient5") val strIngredient5: String?,
            @field:SerializedName("strIngredient6") val strIngredient6: String?,
            @field:SerializedName("strIngredient7") val strIngredient7: String?,
            @field:SerializedName("strIngredient8") val strIngredient8: String?,
            @field:SerializedName("strIngredient9") val strIngredient9: String?,
            @field:SerializedName("strIngredient10") val strIngredient10: String?,
            @field:SerializedName("strIngredient11") val strIngredient11: String?,
            @field:SerializedName("strIngredient12") val strIngredient12: String?,
            @field:SerializedName("strIngredient13") val strIngredient13: String?,
            @field:SerializedName("strIngredient14") val strIngredient14: String?,
            @field:SerializedName("strIngredient15") val strIngredient15: String?,
            @field:SerializedName("strIngredient16") val strIngredient16: String?,
            @field:SerializedName("strIngredient17") val strIngredient17: String?,
            @field:SerializedName("strIngredient18") val strIngredient18: String?,
            @field:SerializedName("strIngredient19") val strIngredient19: String?,
            @field:SerializedName("strIngredient20") val strIngredient20: String?,
            @field:SerializedName("strMeasure1") val strMeasure1: String?,
            @field:SerializedName("strMeasure2") val strMeasure2: String?,
            @field:SerializedName("strMeasure3") val strMeasure3: String?,
            @field:SerializedName("strMeasure4") val strMeasure4: String?,
            @field:SerializedName("strMeasure5") val strMeasure5: String?,
            @field:SerializedName("strMeasure6") val strMeasure6: String?,
            @field:SerializedName("strMeasure7") val strMeasure7: String?,
            @field:SerializedName("strMeasure8") val strMeasure8: String?,
            @field:SerializedName("strMeasure9") val strMeasure9: String?,
            @field:SerializedName("strMeasure10") val strMeasure10: String?,
            @field:SerializedName("strMeasure11") val strMeasure11: String?,
            @field:SerializedName("strMeasure12") val strMeasure12: String?,
            @field:SerializedName("strMeasure13") val strMeasure13: String?,
            @field:SerializedName("strMeasure14") val strMeasure14: String?,
            @field:SerializedName("strMeasure15") val strMeasure15: String?,
            @field:SerializedName("strMeasure16") val strMeasure16: String?,
            @field:SerializedName("strMeasure17") val strMeasure17: String?,
            @field:SerializedName("strMeasure18") val strMeasure18: String?,
            @field:SerializedName("strMeasure19") val strMeasure19: String?,
            @field:SerializedName("strMeasure20") val strMeasure20: String?,
            @field:SerializedName("strSource") val strSource: String?
    )
}

fun List<AllFood.Meal>.toDatabaseModel(): MutableList<LocalAllFoodData> {
    return this.map {
        LocalAllFoodData(it.idMeal!!, it.strMeal, it.strDrinkAlternate, it.strCategory, it.strArea, it.strInstructions,
                it.strMealThumb, it.strTags, it.strYoutube, it.strIngredient1, it.strIngredient2, it.strIngredient3, it.strIngredient4, it.strIngredient5,
                it.strIngredient6, it.strIngredient7, it.strIngredient8, it.strIngredient9, it.strIngredient10, it.strIngredient11, it.strIngredient12,
                it.strIngredient13, it.strIngredient14, it.strIngredient15, it.strIngredient16, it.strIngredient17, it.strIngredient18, it.strIngredient19, it.strIngredient20,
                it.strMeasure1, it.strMeasure2, it.strMeasure3, it.strMeasure4, it.strMeasure5, it.strMeasure6, it.strMeasure7, it.strMeasure8, it.strMeasure9, it.strMeasure10,
                it.strMeasure11, it.strMeasure12, it.strMeasure13, it.strMeasure14, it.strMeasure15, it.strMeasure16, it.strMeasure17, it.strMeasure18, it.strMeasure19, it.strMeasure20,
                it.strSource)
    }.toMutableList()

}