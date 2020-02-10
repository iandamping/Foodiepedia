package com.ian.junemon.foodiepedia.core.cache.util.dto

import com.ian.junemon.foodiepedia.core.cache.model.Food
import com.ian.junemon.foodiepedia.core.cache.model.SavedFood
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.SavedFoodCacheDomain

/**
 * Created by Ian Damping on 27,January,2020
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
        foodInstruction,
        foodIngredient
    )

fun SavedFood.mapToDetailDatabase():SavedFoodCacheDomain = SavedFoodCacheDomain(localFoodID, foodId, foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)

fun SavedFoodCacheDomain.mapToDatabase(): SavedFood =
    SavedFood(
        localFoodID,
        foodId,
        foodName,
        foodCategory,
        foodArea,
        foodImage,
        foodContributor,
        foodInstruction,
        foodIngredient
    )


fun List<FoodCacheDomain>.mapToDatabase(): List<Food> = map { it.mapToDatabase() }

fun List<SavedFood>.mapToDetailDatabase():List<SavedFoodCacheDomain> = map { it.mapToDetailDatabase() }

fun Food.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodInstruction,
    foodIngredient
)

fun List<Food>.mapToCacheDomain(): List<FoodCacheDomain> = map { it.mapToCacheDomain() }
