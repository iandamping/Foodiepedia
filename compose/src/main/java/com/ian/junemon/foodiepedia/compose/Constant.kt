package com.ian.junemon.foodiepedia.compose

import com.ian.junemon.foodiepedia.compose.Constant.FILTER_0
import com.ian.junemon.foodiepedia.compose.state.FilterItem

object Constant {
    const val FILTER_0 = "Everything"
    const val FILTER_1 = "Breakfast"
    const val FILTER_2 = "Lunch"
    const val FILTER_3 = "Dinner"
    const val FILTER_4 = "Brunch"
    const val FILTER_5 = "Supper"

    val FILTER_ITEM = listOf<FilterItem>(
        FilterItem(
            filterIcon = R.drawable.ic_filter_0,
            filterText = FILTER_0,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.ic_filter_1,
            filterText = FILTER_1,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.ic_filter_2,
            filterText = FILTER_2,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.ic_filter_3,
            filterText = FILTER_3,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.ic_filter_4,
            filterText = FILTER_4,
            isFilterSelected = false
        ),
        FilterItem(
            filterIcon = R.drawable.ic_filter_5,
            filterText = FILTER_5,
            isFilterSelected = false
        ),
    )

    fun configureFilterItem(selectedFilter: String): List<FilterItem> =
        FILTER_ITEM.map { it.setIsSelected(selectedFilter) }
}

fun FilterItem.setIsSelected(selectedFilter: String): FilterItem =
    when (selectedFilter) {
        this.filterText -> FilterItem(
            filterIcon = this.filterIcon,
            filterText = this.filterText,
            isFilterSelected = true)

        else -> FilterItem(
            filterIcon = this.filterIcon,
            filterText = this.filterText,
            isFilterSelected = false
        )
    }