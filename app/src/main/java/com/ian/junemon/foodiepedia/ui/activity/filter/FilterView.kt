package com.ian.junemon.foodiepedia.ui.activity.filter

import com.ian.junemon.foodiepedia.base.BaseView
import com.ian.junemon.foodiepedia.model.FilterFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface FilterView : BaseView {
    fun onGetFilterData(data: List<FilterFood.Meal>)
}