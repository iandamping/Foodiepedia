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

    const val ADMIN_1 = "Pw1oWAkBNxRFZqZRZymAfjGe4sK2"
    const val ADMIN_2 = "wrffhDN5OQaGa5NK6JF8S3RQSf73"

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