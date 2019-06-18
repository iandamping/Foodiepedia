package com.ian.junemon.foodiepedia.util

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

object Constant {
    const val areaDetail = "go to area"
    const val categoryDetail = "go to category"
    const val ingredientDetail = "go to ingredient"
    val baseUrl = SecretKeyHelper.BaseUrl
    const val detailFood = "lookup.php"
    const val getAllFood = "latest.php"
    const val getRandomFood = "random.php"
    const val getAllFoodCategoryDetail = "categories.php"
    const val getAllFoodCategory = "list.php"
    const val getAllFoodArea = "list.php"
    const val getAllFoodIngredient = "list.php"
    const val getFilterData = "filter.php"
    val allCategoryValue = SecretKeyHelper.AllCategoryValue
    val categoryType = SecretKeyHelper.CategoryType
    val areaType = SecretKeyHelper.AreaType
    val ingredientType = SecretKeyHelper.IngredientType
    const val intentDetailKey = "detailfood"
    const val delayMillis = 3000L
    const val prefHelperInit = " init preference helper"
    const val saveUserProfile = " save user profile"
    const val RequestSignIn = 2341
    const val switchBackToMain = " switching back"
    const val checkYourConnection = "Please check your connection"
}