package com.ian.junemon.foodiepedia.data.local_data.ingredient

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "ingredient_data")
data class LocalIngredientData constructor(@PrimaryKey
                                           @ColumnInfo(name = "ingredient_id_ingredient") val id: String,
                                           @ColumnInfo(name = "ingredient_str_ingredient") val strIngredient: String?,
                                           @ColumnInfo(name = "ingredient_str_description") val strDescription: String?,
                                           @ColumnInfo(name = "ingredient_str_type") val strType: String?)