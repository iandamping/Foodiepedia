package com.ian.junemon.foodiepedia.util

import androidx.recyclerview.widget.DiffUtil
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
object FoodConstant {
    const val FILENAME = "Foodiepedia"
    const val PHOTO_EXTENSION = ".jpg"


    /** Milliseconds used for UI animations */
    const val ANIMATION_FAST_MILLIS = 50L
    const val ANIMATION_SLOW_MILLIS = 100L

    const val VERTICAL = 0
    const val HORIZONTAL = 1
    const val GRID = 2

    val foodPresentationRvCallback = object : DiffUtil.ItemCallback<FoodCachePresentation?>() {
        override fun areItemsTheSame(
            oldItem: FoodCachePresentation,
            newItem: FoodCachePresentation
        ): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(
            oldItem: FoodCachePresentation,
            newItem: FoodCachePresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
}