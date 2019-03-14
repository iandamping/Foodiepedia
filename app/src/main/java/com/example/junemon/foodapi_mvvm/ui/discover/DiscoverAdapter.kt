package com.example.junemon.foodapi_mvvm.ui.discover

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.inflates
import com.example.junemon.foodapi_mvvm.util.loadUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_discover_food.*

class DiscoverAdapter(
    var data: List<AllFoodCategoryDetail.Category>,
    var listener: (AllFoodCategoryDetail.Category) -> Unit
) :
    RecyclerView.Adapter<DiscoverAdapter.AllFoodCategoryDetailViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFoodCategoryDetailViewHolder {
        return AllFoodCategoryDetailViewHolder(parent.inflates(R.layout.item_discover_food))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AllFoodCategoryDetailViewHolder, position: Int) {
        holder.bind(data.get(position), listener)
    }


    class AllFoodCategoryDetailViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(data: AllFoodCategoryDetail.Category, listener: (AllFoodCategoryDetail.Category) -> Unit) {
            tvDiscoverFoodCategory.text = data.strCategory
            tvDiscoverFoodDescription.text = data.strCategoryDescription
            ivDiscoverFood.loadUrl(data.strCategoryThumb)
            itemView.setOnClickListener { listener(data) }
        }
    }
}