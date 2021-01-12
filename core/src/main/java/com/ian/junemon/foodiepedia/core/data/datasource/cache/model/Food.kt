package com.ian.junemon.foodiepedia.core.data.datasource.cache.model

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
    @ColumnInfo(name = "food_description") val foodDescription: String?
)