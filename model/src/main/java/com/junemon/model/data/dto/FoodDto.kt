package com.junemon.model.data.dto

import com.junemon.model.data.FoodEntity
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import com.junemon.model.presentation.FoodCachePresentation
import com.junemon.model.presentation.SavedFoodCachePresentation

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun FoodEntity.mapToRemoteDomain():FoodRemoteDomain = FoodRemoteDomain(foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)
fun FoodRemoteDomain.mapToCacheDomain():FoodCacheDomain = FoodCacheDomain(null,foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)
fun FoodCachePresentation.mapToDetailDatabasePresentation() = SavedFoodCacheDomain(null,foodId, foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)
fun FoodCacheDomain.mapToCachePresentation(): FoodCachePresentation =
    FoodCachePresentation(
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodInstruction,
        foodIngredient
    )

fun List<FoodRemoteDomain>.mapRemoteToCacheDomain(): List<FoodCacheDomain> =
    map { it.mapToCacheDomain() }



fun List<FoodEntity>.mapToRemoteDomain(): List<FoodRemoteDomain> =
    map { it.mapToRemoteDomain() }

fun List<FoodCacheDomain>.mapToCachePresentation(): List<FoodCachePresentation> =
    map { it.mapToCachePresentation() }