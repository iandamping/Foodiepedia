package com.ian.junemon.foodiepedia.state

import androidx.annotation.DrawableRes

data class FilterItem(
    @DrawableRes val filterIcon: Int,
    val filterText: String,
    var isFilterSelected: Boolean
)
