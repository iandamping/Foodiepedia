package com.ian.junemon.foodiepedia.feature.view.home

import androidx.recyclerview.widget.RecyclerView
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.databinding.ItemCustomHomeBinding
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeViewHolder(
    private val itemBinding: ItemCustomHomeBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: FoodCachePresentation) {
        with(loadImageHelper) { itemBinding.ivFoodImage.loadWithGlide(data.foodImage) }
        itemBinding.tvTitle.text = data.foodName
        itemBinding.tvDescription.text = data.foodDescription
    }
}