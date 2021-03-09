package com.ian.junemon.foodiepedia.feature.view.search

import androidx.recyclerview.widget.RecyclerView
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.databinding.ItemHomeBinding
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchViewHolder(
    private val itemHomeBinding: ItemHomeBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(itemHomeBinding.root) {

    fun bind(data: FoodCachePresentation) {
        with(this) {
            loadImageHelper.run { itemHomeBinding.ivFoodImage.loadWithGlide(data.foodImage) }
            itemHomeBinding.tvFoodContributor.text = data.foodContributor
        }
    }
}