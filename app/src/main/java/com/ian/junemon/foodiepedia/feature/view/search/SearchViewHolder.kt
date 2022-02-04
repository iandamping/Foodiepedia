package com.ian.junemon.foodiepedia.feature.view.search

import androidx.recyclerview.widget.RecyclerView
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.ItemSearchBinding

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchViewHolder(
    private val itemHomeBinding: ItemSearchBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(itemHomeBinding.root) {

    fun bind(data: FoodCachePresentation) {
        with(itemHomeBinding) {
            loadImageHelper.loadWithGlide(ivFoodImage, data.foodImage)
            tvFoodName.text = data.foodName
        }
    }
}