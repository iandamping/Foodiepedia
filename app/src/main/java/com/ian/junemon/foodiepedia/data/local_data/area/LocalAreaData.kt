package com.ian.junemon.foodiepedia.data.local_data.area

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *
Created by Ian Damping on 17/06/2019.
Github = https://github.com/iandamping
 */
@Entity(tableName = "area_data")
data class LocalAreaData constructor(@PrimaryKey
                                     @ColumnInfo(name = "area_str_area") val strArea: String)