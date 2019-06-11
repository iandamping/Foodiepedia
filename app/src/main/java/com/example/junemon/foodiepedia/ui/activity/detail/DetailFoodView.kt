package com.example.junemon.foodiepedia.ui.activity.detail

import com.example.junemon.foodiepedia.base.BaseView
import com.example.junemon.foodiepedia.data.local_data.LocalFoodData
import com.example.junemon.foodiepedia.model.DetailFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DetailFoodView : BaseView {
    fun showDetailData(data: DetailFood.Meal)
    fun isAlreadyLoggedin(data:Boolean)
    fun showIngredientData(dataIngredient: List<String>, dataMeasurement: List<String>)
    fun onSuccessGetLocalData(data: List<LocalFoodData>)
}