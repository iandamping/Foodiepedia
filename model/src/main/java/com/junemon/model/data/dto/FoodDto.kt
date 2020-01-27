package com.junemon.model.data.dto

import com.junemon.model.data.FoodEntity
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun FoodEntity.mapToRemoteDomain():FoodRemoteDomain = FoodRemoteDomain(foodName, foodCategory, foodArea,foodImage,foodContributor, foodInstruction, foodIngredient1, foodIngredient2, foodIngredient3, foodIngredient4, foodIngredient5, foodIngredient6, foodIngredient7, foodIngredient8, foodIngredient9, foodIngredient10, foodIngredient11, foodIngredient12, foodIngredient13, foodIngredient14, foodIngredient15, foodIngredient16, foodIngredient17, foodIngredient18, foodIngredient19, foodIngredient20)
fun FoodRemoteDomain.mapToCacheDomain():FoodCacheDomain = FoodCacheDomain(null,foodName, foodCategory, foodArea, foodImage,foodContributor,foodInstruction, foodIngredient1, foodIngredient2, foodIngredient3, foodIngredient4, foodIngredient5, foodIngredient6, foodIngredient7, foodIngredient8, foodIngredient9, foodIngredient10, foodIngredient11, foodIngredient12, foodIngredient13, foodIngredient14, foodIngredient15, foodIngredient16, foodIngredient17, foodIngredient18, foodIngredient19, foodIngredient20)

fun List<FoodRemoteDomain>.mapRemoteToCacheDomain(): List<FoodCacheDomain> =
    map { it.mapToCacheDomain() }

fun List<FoodEntity>.mapToRemoteDomain(): List<FoodRemoteDomain> =
    map { it.mapToRemoteDomain() }