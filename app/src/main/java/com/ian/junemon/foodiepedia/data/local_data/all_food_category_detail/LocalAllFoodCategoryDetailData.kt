package com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "all_food_category_detail")
data class LocalAllFoodCategoryDetailData constructor(
        @PrimaryKey
        @ColumnInfo(name = "all_food_category_id_category") val idCategory: String,
        @ColumnInfo(name = "all_food_category_str_category") val strCategory: String?,
        @ColumnInfo(name = "all_food_category_str_category_thumb") val strCategoryThumb: String?,
        @ColumnInfo(name = "all_food_category_str_category_description") val strCategoryDescription: String?)