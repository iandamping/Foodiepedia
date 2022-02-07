package com.ian.junemon.foodiepedia.compose.state

import androidx.annotation.DrawableRes

data class FilterItem(
    @DrawableRes val filterIcon: Int,
    val filterText: String,
    var isFilterSelected: Boolean
)
