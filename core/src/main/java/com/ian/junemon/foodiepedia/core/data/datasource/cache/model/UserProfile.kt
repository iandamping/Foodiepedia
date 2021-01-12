package com.ian.junemon.foodiepedia.core.data.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_userdata")
data class UserProfile(
    @PrimaryKey(autoGenerate = true) var localID: Int?,
    @ColumnInfo(name = "profile_id") var userID: String?,
    @ColumnInfo(name = "profile_photo") var photoUser: String?,
    @ColumnInfo(name = "profile_name") var nameUser: String?,
    @ColumnInfo(name = "profile_email") var emailUser: String?
)