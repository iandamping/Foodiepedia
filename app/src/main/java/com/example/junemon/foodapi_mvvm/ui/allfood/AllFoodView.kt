package com.example.junemon.foodapi_mvvm.ui.allfood

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFood
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface AllFoodView : BaseView {
    fun getDetailFood(data: List<AllFood.Meal>?)
}