package com.ian.junemon.foodiepedia.core.domain.model

data class FoodRemoteDomain(
    var foodName: String?,
    var foodCategory: String?,
    var foodArea: String?,
    var foodImage: String?,
    var foodContributor: String?,
    var foodDescription: String?
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null
    )
}