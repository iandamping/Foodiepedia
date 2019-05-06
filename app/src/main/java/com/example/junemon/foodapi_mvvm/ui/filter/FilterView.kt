package com.example.junemon.foodapi_mvvm.ui.filter

import com.example.junemon.foodapi_mvvm.base.BaseView
import com.example.junemon.foodapi_mvvm.model.FilterFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface FilterView : BaseView {
    fun onGetFilterData(data: List<FilterFood.Meal>)
}