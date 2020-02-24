package com.junemon.model.domain

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class SavedFoodCacheDomain(
    val localFoodID:Int?,
    val foodId: Int?,
    val foodName: String?,
    val foodCategory: String?,
    val foodArea: String?,
    val foodImage: String?,
    val foodContributor: String?,
    val foodDescription: String?
)