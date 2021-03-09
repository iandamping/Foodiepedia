package com.ian.junemon.foodiepedia.feature.view.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.databinding.ItemSearchBinding
import com.ian.junemon.foodiepedia.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 09,March,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SearchAdapter @Inject constructor(
    private val listener: SearchAdapterListener,
    private val loadImageHelper: LoadImageHelper
) : ListAdapter<FoodCachePresentation, SearchViewHolder>(foodPresentationRvCallback) {

    interface SearchAdapterListener {
        fun onClicked(data: FoodCachePresentation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            ItemSearchBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            loadImageHelper
        )
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.itemView.setOnClickListener {
            listener.onClicked(data)
        }
    }
}