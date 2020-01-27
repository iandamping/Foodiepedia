package com.ian.junemon.foodiepedia.core.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Entity(tableName = "table_food")
data class Food(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "food_id") val foodId: Int?,
    @ColumnInfo(name = "food_name") val foodName: String?,
    @ColumnInfo(name = "food_category") val foodCategory: String?,
    @ColumnInfo(name = "food_area") val foodArea: String?,
    @ColumnInfo(name = "food_image") val foodImage: String?,
    @ColumnInfo(name = "food_contributor") val foodContributor: String?,
    @ColumnInfo(name = "food_instruction") val foodInstruction: String?,
    @ColumnInfo(name = "food_ingredients_1") val foodIngredient1: String?,
    @ColumnInfo(name = "food_ingredients_2") val foodIngredient2: String?,
    @ColumnInfo(name = "food_ingredients_3") val foodIngredient3: String?,
    @ColumnInfo(name = "food_ingredients_4") val foodIngredient4: String?,
    @ColumnInfo(name = "food_ingredients_5") val foodIngredient5: String?,
    @ColumnInfo(name = "food_ingredients_6") val foodIngredient6: String?,
    @ColumnInfo(name = "food_ingredients_7") val foodIngredient7: String?,
    @ColumnInfo(name = "food_ingredients_8") val foodIngredient8: String?,
    @ColumnInfo(name = "food_ingredients_9") val foodIngredient9: String?,
    @ColumnInfo(name = "food_ingredients_10") val foodIngredient10: String?,
    @ColumnInfo(name = "food_ingredients_11") val foodIngredient11: String?,
    @ColumnInfo(name = "food_ingredients_12") val foodIngredient12: String?,
    @ColumnInfo(name = "food_ingredients_13") val foodIngredient13: String?,
    @ColumnInfo(name = "food_ingredients_14") val foodIngredient14: String?,
    @ColumnInfo(name = "food_ingredients_15") val foodIngredient15: String?,
    @ColumnInfo(name = "food_ingredients_16") val foodIngredient16: String?,
    @ColumnInfo(name = "food_ingredients_17") val foodIngredient17: String?,
    @ColumnInfo(name = "food_ingredients_18") val foodIngredient18: String?,
    @ColumnInfo(name = "food_ingredients_19") val foodIngredient19: String?,
    @ColumnInfo(name = "food_ingredients_20") val foodIngredient20: String?
)