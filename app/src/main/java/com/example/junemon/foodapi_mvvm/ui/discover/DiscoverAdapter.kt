package com.example.junemon.foodapi_mvvm.ui.discover

import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.loadUrl
import com.example.junemon.foodapi_mvvm.util.setUpWithGrid
import kotlinx.android.synthetic.main.item_discover_food.view.*

class DiscoverAdapter(
    view: RecyclerView,
    data: List<AllFoodCategoryDetail.Category>?,
    layout: Int,
    private val listener: (AllFoodCategoryDetail.Category) -> Unit
) {
    init {
        data?.let { data ->
            view.setUpWithGrid(data, layout, 2, {
                tvDiscoverFoodCategory.text = it.strCategory
                tvDiscoverFoodDescription.text = it.strCategoryDescription
                ivDiscoverFood.loadUrl(it.strCategoryThumb)
            }, listener)
        }
    }

}