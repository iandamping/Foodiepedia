package com.example.junemon.foodapi_mvvm.ui.activity.detail

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.data.local_data.LocalFoodData
import com.example.junemon.foodapi_mvvm.model.DetailFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DetailFoodView : BaseView {
    fun showDetailData(data: DetailFood.Meal)
    fun showIngredientData(dataIngredient: List<String>, dataMeasurement: List<String>)
    fun onSuccessGetLocalData(data: List<LocalFoodData>)
}