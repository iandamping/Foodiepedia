package com.ian.junemon.foodiepedia.core.data.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Entity(tableName = "table_saved_food")
data class SavedFood(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "saved_local_food_id")var localFoodID: Int?,
    @ColumnInfo(name = "saved_remote_food_id") val foodId: Int?,
    @ColumnInfo(name = "saved_food_name") val foodName: String?,
    @ColumnInfo(name = "saved_food_category") val foodCategory: String?,
    @ColumnInfo(name = "saved_food_area") val foodArea: String?,
    @ColumnInfo(name = "saved_food_image") val foodImage: String?,
    @ColumnInfo(name = "saved_food_contributor") val foodContributor: String?,
    @ColumnInfo(name = "saved_food_description") val foodDescription: String?
)