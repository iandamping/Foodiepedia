package com.example.junemon.foodapi_mvvm.ui.detail

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.DetailFood

interface DetailFoodView : BaseView {
    fun showDetailData(data: DetailFood.Meal)
    fun showIngredientData(dataIngredient: List<String>, dataMeasurement: List<String>)
}