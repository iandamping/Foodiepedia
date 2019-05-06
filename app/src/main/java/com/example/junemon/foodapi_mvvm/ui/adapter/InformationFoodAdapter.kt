package com.example.junemon.foodapi_mvvm.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.util.setUpWithGrid
import kotlinx.android.synthetic.main.item_information_category.view.*

class InformationFoodAdapter(
        view: RecyclerView,
        data: List<CategoryFood.Meal>?,
        layout: Int,
        private val listener: (CategoryFood.Meal) -> Unit
) {
    init {
        data?.let { datas ->
            view.setUpWithGrid(
                    datas, layout, 3, {
                tvDescriptionCategory.text = it.category
            }, listener
            )
        }
    }
}