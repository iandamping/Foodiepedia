package com.ian.junemon.foodiepedia.core.cache.util.dto

import com.ian.junemon.foodiepedia.core.cache.model.UserProfile
import com.junemon.model.data.UserProfileEntity
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
fun UserProfileDataModel.mapToDatabase() =
    UserProfile(localID, userID, photoUser, nameUser, emailUser)

fun UserProfileEntity.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun UserProfile.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun Flow<UserProfile>.mapToDomain() = map { it?.mapToDomain() }