package com.ian.junemon.foodiepedia.ui.activity.detailinformation

import com.ian.junemon.foodiepedia.base.BaseView
import com.ian.junemon.foodiepedia.model.AreaFood
import com.ian.junemon.foodiepedia.model.CategoryFood
import com.ian.junemon.foodiepedia.model.IngredientFood

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