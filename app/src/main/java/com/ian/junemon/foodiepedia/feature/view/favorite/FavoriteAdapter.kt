package com.ian.junemon.foodiepedia.feature.view.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.ItemFavoriteBinding
import com.ian.junemon.foodiepedia.util.FoodConstant
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * Created by Ian Damping on 11,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FavoriteAdapter @AssistedInject constructor(
    @Assisted private val listener: FavoriteAdapterListener,
    private val loadImageHelper: LoadImageHelper
) : ListAdapter<FoodCachePresentation, FavoriteViewHolder>(FoodConstant.foodPresentationRvCallback) {

    @AssistedFactory
    interface Factory {
        fun create(listener: FavoriteAdapterListener): FavoriteAdapter
    }

    interface FavoriteAdapterListener {
        fun onClicked(data: FoodCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            ItemFavoriteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            loadImageHelper
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }
}