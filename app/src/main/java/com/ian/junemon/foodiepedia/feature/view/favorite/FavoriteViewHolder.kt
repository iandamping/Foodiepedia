package com.ian.junemon.foodiepedia.feature.view.favorite

import androidx.recyclerview.widget.RecyclerView
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.ItemFavoriteBinding

/**
 * Created by Ian Damping on 11,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FavoriteViewHolder(
    private val itemSliderBinding: ItemFavoriteBinding,
    private val loadImageHelper: LoadImageHelper
) : RecyclerView.ViewHolder(itemSliderBinding.root) {

    fun bind(data: FoodCachePresentation) {
        with(itemSliderBinding) {
            loadImageHelper.loadWithGlide(ivFavoriteFoodImage, data.foodImage)
            tvFavoriteFoodName.text = data.foodName
        }
    }
}