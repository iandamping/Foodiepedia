package com.example.junemon.foodapi_mvvm.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.model.IngredientFood
import com.example.junemon.foodapi_mvvm.util.setUpWithGrid
import kotlinx.android.synthetic.main.item_information_ingredient.view.*

class InformationIngredientAdapter(
        view: RecyclerView,
        data: List<IngredientFood.Meal>?,
        layout: Int,
        private val listener: (IngredientFood.Meal) -> Unit
) {
    init {
        data?.let { datas ->
            view.setUpWithGrid(
                    datas, layout, 3, {
                tvInformationIngredient.text = it.strIngredient
            }, listener
            )
        }
    }
}