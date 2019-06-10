package com.example.junemon.foodiepedia.ui.activity.filter

import com.example.junemon.foodiepedia.base.BaseView
import com.example.junemon.foodiepedia.model.FilterFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface FilterView : BaseView {
    fun onGetFilterData(data: List<FilterFood.Meal>)
}