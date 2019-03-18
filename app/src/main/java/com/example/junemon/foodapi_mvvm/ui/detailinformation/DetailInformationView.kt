package com.example.junemon.foodapi_mvvm.ui.detailinformation

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood

interface DetailInformationView : BaseView {
    fun getAreaData(data: List<AreaFood.Meal>?)
    fun getIngredientData(data: List<IngredientFood.Meal>?)
    fun getCategoryData(data: List<CategoryFood.Meal>?)
}