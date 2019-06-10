package com.example.junemon.foodiepedia.ui.activity.discover

import com.example.junemon.foodiepedia.base.BaseView
import com.example.junemon.foodiepedia.model.AllFoodCategoryDetail

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DiscoverView : BaseView {
    fun onShowDefaultFoodCategory(data: List<AllFoodCategoryDetail.Category>?)
}