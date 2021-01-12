package com.ian.junemon.foodiepedia.core.data.model.data.dto

import com.ian.junemon.foodiepedia.core.data.model.data.FoodEntity
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.SavedFoodCachePresentation

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun FoodEntity.mapToRemoteDomain(): FoodRemoteDomain = FoodRemoteDomain(foodName, foodCategory, foodArea, foodImage, foodContributor, foodDescription)
fun FoodRemoteDomain.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(null,foodName, foodCategory, foodArea, foodImage, foodContributor, foodDescription)
fun FoodCachePresentation.mapToDetailDatabasePresentation() = SavedFoodCacheDomain(null,foodId, foodName, foodCategory, foodArea, foodImage, foodContributor,foodDescription)
fun FoodCacheDomain.mapToCachePresentation(): FoodCachePresentation =
    FoodCachePresentation(
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodDescription
    )

fun List<FoodRemoteDomain>.mapRemoteToCacheDomain(): List<FoodCacheDomain> =
    map { it.mapToCacheDomain() }



fun List<FoodEntity>.mapToRemoteDomain(): List<FoodRemoteDomain> =
    map { it.mapToRemoteDomain() }

fun List<FoodCacheDomain>.mapToCachePresentation(): List<FoodCachePresentation> =
    map { it.mapToCachePresentation() }