package com.ian.junemon.foodiepedia.data.local_data.filter

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "filter_data")
data class LocalFilterData constructor(@PrimaryKey
                                       @ColumnInfo(name = "filter_id_meal") val idMeal: String,
                                       @ColumnInfo(name = "filter_str_meal") val strMeal: String?,
                                       @ColumnInfo(name = "filter_str_meal_thumb") val strMealThumb: String)