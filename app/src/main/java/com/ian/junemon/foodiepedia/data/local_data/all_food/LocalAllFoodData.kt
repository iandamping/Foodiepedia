package com.ian.junemon.foodiepedia.data.local_data.all_food

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "all_food_data")
data class LocalAllFoodData constructor(
        @PrimaryKey
        @ColumnInfo(name = "all_food_id_meal") val idMeal: String,
        @ColumnInfo(name = "all_food_str_meal") val strMeal: String?,
        @ColumnInfo(name = "all_food_str_drink_alternate") val strDrinkAlternate: String?,
        @ColumnInfo(name = "all_food_str_category") val strCategory: String?,
        @ColumnInfo(name = "all_food_str_area") val strArea: String?,
        @ColumnInfo(name = "all_food_str_instruction") val strInstructions: String?,
        @ColumnInfo(name = "all_food_str_meal_thumb") val strMealThumb: String?,
        @ColumnInfo(name = "all_food_str_tags") val strTags: String?,
        @ColumnInfo(name = "all_food_str_youtube") val strYoutube: String?,
        @ColumnInfo(name = "all_food_str_ingredients_1") val strIngredient1: String?,
        @ColumnInfo(name = "all_food_str_ingredients_2") val strIngredient2: String?,
        @ColumnInfo(name = "all_food_str_ingredients_3") val strIngredient3: String?,
        @ColumnInfo(name = "all_food_str_ingredients_4") val strIngredient4: String?,
        @ColumnInfo(name = "all_food_str_ingredients_5") val strIngredient5: String?,
        @ColumnInfo(name = "all_food_str_ingredients_6") val strIngredient6: String?,
        @ColumnInfo(name = "all_food_str_ingredients_7") val strIngredient7: String?,
        @ColumnInfo(name = "all_food_str_ingredients_8") val strIngredient8: String?,
        @ColumnInfo(name = "all_food_str_ingredients_9") val strIngredient9: String?,
        @ColumnInfo(name = "all_food_str_ingredients_10") val strIngredient10: String?,
        @ColumnInfo(name = "all_food_str_ingredients_11") val strIngredient11: String?,
        @ColumnInfo(name = "all_food_str_ingredients_12") val strIngredient12: String?,
        @ColumnInfo(name = "all_food_str_ingredients_13") val strIngredient13: String?,
        @ColumnInfo(name = "all_food_str_ingredients_14") val strIngredient14: String?,
        @ColumnInfo(name = "all_food_str_ingredients_15") val strIngredient15: String?,
        @ColumnInfo(name = "all_food_str_ingredients_16") val strIngredient16: String?,
        @ColumnInfo(name = "all_food_str_ingredients_17") val strIngredient17: String?,
        @ColumnInfo(name = "all_food_str_ingredients_18") val strIngredient18: String?,
        @ColumnInfo(name = "all_food_str_ingredients_19") val strIngredient19: String?,
        @ColumnInfo(name = "all_food_str_ingredients_20") val strIngredient20: String?,
        @ColumnInfo(name = "all_food_str_measure_1") val strMeasure1: String?,
        @ColumnInfo(name = "all_food_str_measure_2") val strMeasure2: String?,
        @ColumnInfo(name = "all_food_str_measure_3") val strMeasure3: String?,
        @ColumnInfo(name = "all_food_str_measure_4") val strMeasure4: String?,
        @ColumnInfo(name = "all_food_str_measure_5") val strMeasure5: String?,
        @ColumnInfo(name = "all_food_str_measure_6") val strMeasure6: String?,
        @ColumnInfo(name = "all_food_str_measure_7") val strMeasure7: String?,
        @ColumnInfo(name = "all_food_str_measure_8") val strMeasure8: String?,
        @ColumnInfo(name = "all_food_str_measure_9") val strMeasure9: String?,
        @ColumnInfo(name = "all_food_str_measure_10") val strMeasure10: String?,
        @ColumnInfo(name = "all_food_str_measure_11") val strMeasure11: String?,
        @ColumnInfo(name = "all_food_str_measure_12") val strMeasure12: String?,
        @ColumnInfo(name = "all_food_str_measure_13") val strMeasure13: String?,
        @ColumnInfo(name = "all_food_str_measure_14") val strMeasure14: String?,
        @ColumnInfo(name = "all_food_str_measure_15") val strMeasure15: String?,
        @ColumnInfo(name = "all_food_str_measure_16") val strMeasure16: String?,
        @ColumnInfo(name = "all_food_str_measure_17") val strMeasure17: String?,
        @ColumnInfo(name = "all_food_str_measure_18") val strMeasure18: String?,
        @ColumnInfo(name = "all_food_str_measure_19") val strMeasure19: String?,
        @ColumnInfo(name = "all_food_str_measure_20") val strMeasure20: String?,
        @ColumnInfo(name = "all_food_str_source") val strSource: String?
) {
}