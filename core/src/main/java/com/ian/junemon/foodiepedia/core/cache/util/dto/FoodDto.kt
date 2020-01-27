package com.ian.junemon.foodiepedia.core.cache.util.dto

import com.ian.junemon.foodiepedia.core.cache.model.Food
import com.junemon.model.domain.FoodCacheDomain

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
        foodIngredient1,
        foodIngredient2,
        foodIngredient3,
        foodIngredient4,
        foodIngredient5,
        foodIngredient6,
        foodIngredient7,
        foodIngredient8,
        foodIngredient9,
        foodIngredient10,
        foodIngredient11,
        foodIngredient12,
        foodIngredient13,
        foodIngredient14,
        foodIngredient15,
        foodIngredient16,
        foodIngredient17,
        foodIngredient18,
        foodIngredient19,
        foodIngredient20
    )

fun List<FoodCacheDomain>.mapToDatabase(): List<Food> = map { it.mapToDatabase() }

fun Food.mapToCacheDomain(): FoodCacheDomain = FoodCacheDomain(
    foodId,
    foodName,
    foodCategory,
    foodArea,
    foodImage,
    foodContributor,
    foodInstruction,
    foodIngredient1,
    foodIngredient2,
    foodIngredient3,
    foodIngredient4,
    foodIngredient5,
    foodIngredient6,
    foodIngredient7,
    foodIngredient8,
    foodIngredient9,
    foodIngredient10,
    foodIngredient11,
    foodIngredient12,
    foodIngredient13,
    foodIngredient14,
    foodIngredient15,
    foodIngredient16,
    foodIngredient17,
    foodIngredient18,
    foodIngredient19,
    foodIngredient20
)

fun List<Food>.mapToCacheDomain(): List<FoodCacheDomain> = map { it.mapToCacheDomain() }
