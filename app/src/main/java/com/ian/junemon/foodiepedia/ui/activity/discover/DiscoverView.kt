package com.ian.junemon.foodiepedia.ui.activity.discover

import com.ian.junemon.foodiepedia.base.BaseView
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface DiscoverView : BaseView {
    fun onShowDefaultFoodCategory(data: List<LocalAllFoodCategoryDetailData>?)
}