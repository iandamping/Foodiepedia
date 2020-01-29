package com.junemon.model.domain

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class FoodCacheDomain(
    val foodId: Int?,
    val foodName: String?,
    val foodCategory: String?,
    val foodArea: String?,
    val foodImage: String?,
    val foodContributor: String?,
    val foodInstruction: String?,
    val foodIngredient: String?
)