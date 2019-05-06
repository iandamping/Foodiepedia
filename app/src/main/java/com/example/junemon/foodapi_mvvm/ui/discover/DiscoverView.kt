package com.example.junemon.foodapi_mvvm.ui.discover

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DiscoverView : BaseView {
    fun onShowDefaultFoodCategory(data: List<AllFoodCategoryDetail.Category>?)
}