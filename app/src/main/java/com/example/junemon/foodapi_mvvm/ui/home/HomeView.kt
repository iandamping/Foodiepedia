package com.example.junemon.foodapi_mvvm.ui.home

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.model.DetailFood

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
interface HomeView : BaseView {
    fun onGetAllFood(data: List<AllFood.Meal>?)
    fun onGetAllFoodCategoryDetails(data: List<AllFoodCategoryDetail.Category>?)
    fun onGetRandomFood(data: DetailFood.Meal?)
    fun onFailGetAllFood()
    fun onFailGetAllFoodCategoryDetails()


}