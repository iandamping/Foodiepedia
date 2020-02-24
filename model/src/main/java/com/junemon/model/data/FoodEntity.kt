package com.junemon.model.data

/**
 * Created by Ian Damping on 24,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class FoodEntity(
    val foodName: String?,
    val foodCategory: String?,
    val foodArea: String?,
    val foodImage: String?,
    val foodContributor: String?,
    val foodDescription: String?
){
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )
}