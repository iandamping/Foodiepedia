package com.example.junemon.foodapi_mvvm.ui.allfood

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFood

interface AllFoodView : BaseView {
    fun getDetailFood(data: List<AllFood.Meal>?)
}