package com.example.junemon.foodapi_mvvm.ui.allfood

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood

interface AllFoodView : BaseView {
    fun getDetailFood(data: List<AllFood.Meal>?)
    fun getAreaData(data: List<AreaFood.Meal>?)
    fun getIngredientData(data: List<IngredientFood.Meal>?)
    fun getCategoryData(data: List<CategoryFood.Meal>?)
}