package com.ian.junemon.foodiepedia.data.local_data.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "category_data")
data class LocalCategoryData constructor(@PrimaryKey
                                         @ColumnInfo(name = "category_str_category") val category: String)