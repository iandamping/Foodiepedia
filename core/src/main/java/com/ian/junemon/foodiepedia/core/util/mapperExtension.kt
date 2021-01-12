package com.ian.junemon.foodiepedia.core.util

import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.Food
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.SavedFood
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.UserProfile
import com.ian.junemon.foodiepedia.core.data.model.data.UserProfileEntity
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun FoodCacheDomain.mapToDatabase(): Food =
    Food(
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun SavedFood.mapToDetailDatabase(): SavedFoodCacheDomain = SavedFoodCacheDomain(localFoodID, foodId, foodName, foodCategory, foodArea, foodImage, foodContributor, foodDescription)

fun SavedFoodCacheDomain.mapToDatabase(): SavedFood =
    SavedFood(
        localFoodID,
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun List<FoodCacheDomain>.mapToDatabase(): List<Food> = map { it.mapToDatabase() }

fun List<SavedFood>.mapToDetailDatabase(): List<SavedFoodCacheDomain> = map { it.mapToDetailDatabase() }

fun Food.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodDescription
)

fun List<Food>.mapToCacheDomain(): List<FoodCacheDomain> = map { it.mapToCacheDomain() }

fun UserProfileDataModel.mapToDatabase() =
    UserProfile(localID, userID, photoUser, nameUser, emailUser)

fun UserProfileEntity.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun UserProfile.mapToDomain() =
    UserProfileDataModel(localID, userID, photoUser, nameUser, emailUser)

fun Flow<UserProfile>.mapToDomain() = map { it?.mapToDomain() }