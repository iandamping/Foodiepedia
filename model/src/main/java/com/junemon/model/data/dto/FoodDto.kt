package com.junemon.model.data.dto

import com.junemon.model.data.FoodEntity
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */

fun FoodEntity.mapToRemoteDomain():FoodRemoteDomain = FoodRemoteDomain(foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)
fun FoodRemoteDomain.mapToCacheDomain():FoodCacheDomain = FoodCacheDomain(null,foodName, foodCategory, foodArea, foodImage, foodContributor, foodInstruction, foodIngredient)

fun List<FoodRemoteDomain>.mapRemoteToCacheDomain(): List<FoodCacheDomain> =
    map { it.mapToCacheDomain() }



fun List<FoodEntity>.mapToRemoteDomain(): List<FoodRemoteDomain> =
    map { it.mapToRemoteDomain() }