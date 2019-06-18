package com.ian.junemon.foodiepedia.ui.activity.detailinformation

import com.ian.junemon.foodiepedia.base.BaseView
import com.ian.junemon.foodiepedia.data.local_data.area.LocalAreaData
import com.ian.junemon.foodiepedia.data.local_data.category.LocalCategoryData
import com.ian.junemon.foodiepedia.data.local_data.ingredient.LocalIngredientData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DetailInformationView : BaseView {
    fun getAreaData(data: List<LocalAreaData>?)
    fun getIngredientData(data: List<LocalIngredientData>?)
    fun getCategoryData(data: List<LocalCategoryData>?)
}