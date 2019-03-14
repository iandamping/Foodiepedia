package com.example.junemon.foodapi_mvvm.ui.discover

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail

interface DiscoverView : BaseView {
    fun onShowDefaultFoodCategory(data: List<AllFoodCategoryDetail.Category>?)
}