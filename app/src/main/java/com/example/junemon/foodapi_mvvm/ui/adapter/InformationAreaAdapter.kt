package com.example.junemon.foodapi_mvvm.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.util.setUpWithGrid
import kotlinx.android.synthetic.main.item_information_area.view.*

class InformationAreaAdapter(
        view: RecyclerView,
        data: List<AreaFood.Meal>?,
        layout: Int,
        private val listener: (AreaFood.Meal) -> Unit
) {
    init {
        data?.let { datas ->
            view.setUpWithGrid(
                    datas, layout, 3, {
                tvDescriptionArea.text = it.strArea
            }, listener
            )
        }
    }
}