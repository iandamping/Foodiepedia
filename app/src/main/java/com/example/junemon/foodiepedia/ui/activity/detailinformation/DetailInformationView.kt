package com.example.junemon.foodiepedia.ui.activity.detailinformation

import com.example.junemon.foodiepedia.base.BaseView
import com.example.junemon.foodiepedia.model.AreaFood
import com.example.junemon.foodiepedia.model.CategoryFood
import com.example.junemon.foodiepedia.model.IngredientFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DetailInformationView : BaseView {
    fun getAreaData(data: List<AreaFood.Meal>?)
    fun getIngredientData(data: List<IngredientFood.Meal>?)
    fun getCategoryData(data: List<CategoryFood.Meal>?)
}