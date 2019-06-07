package com.example.junemon.foodapi_mvvm.data.local_data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "food_data")
data class LocalFoodData(
        @PrimaryKey(autoGenerate = true) var localID: Int?,
        @ColumnInfo(name = "food_id_meal") var idMeal: String?,
        @ColumnInfo(name = "food_id_name") var strMeal: String?,
        @ColumnInfo(name = "food_id_category") var strCategory: String?
)