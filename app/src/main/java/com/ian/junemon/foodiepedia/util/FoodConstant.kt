package com.ian.junemon.foodiepedia.util

import androidx.recyclerview.widget.DiffUtil
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
object FoodConstant {
    const val requestSignIn = 2341

    val foodDomainRvCallback = object : DiffUtil.ItemCallback<FoodCacheDomain?>() {
        override fun areItemsTheSame(oldItem: FoodCacheDomain, newItem: FoodCacheDomain): Boolean {
            return oldItem.foodId == newItem.foodId
        }

        override fun areContentsTheSame(
            oldItem: FoodCacheDomain,
            newItem: FoodCacheDomain
        ): Boolean {
            return oldItem.foodId == newItem.foodId
        }
    }

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