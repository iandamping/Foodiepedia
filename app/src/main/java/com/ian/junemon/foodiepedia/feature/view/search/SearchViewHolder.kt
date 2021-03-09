package com.ian.junemon.foodiepedia.feature.view.search

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.databinding.ItemHomeBinding
import com.ian.junemon.foodiepedia.util.generateRandomHexColor
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
        with(itemHomeBinding){
            with(loadImageHelper){ ivFoodImage.loadWithGlide(data.foodImage) }
            chipDetail.text = data.foodCategory
            chipDetail.chipBackgroundColor =
                ColorStateList.valueOf(Color.parseColor(generateRandomHexColor()))
        }

    }
}