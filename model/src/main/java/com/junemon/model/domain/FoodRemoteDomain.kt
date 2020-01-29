package com.junemon.model.domain

data class FoodRemoteDomain(
    var foodName: String?,
    var foodCategory: String?,
    var foodArea: String?,
    var foodImage: String?,
    var foodContributor: String?,
    var foodInstruction: String?,
    var foodIngredient: String?
) {
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}