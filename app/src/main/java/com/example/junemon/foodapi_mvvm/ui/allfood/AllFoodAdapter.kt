package com.example.junemon.foodapi_mvvm.ui.allfood

import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.util.loadUrl
import com.example.junemon.foodapi_mvvm.util.setUpWithSkid
import kotlinx.android.synthetic.main.item_all_food.view.*

class AllFoodAdapter(view: RecyclerView, data: List<AllFood.Meal>?, layout: Int, private val listener: (AllFood.Meal) -> Unit) {
    init {
        data?.let { data ->
            view.setUpWithSkid(
                    data, layout, {
                tvAllFoodName.text = it.strMeal
                tvAllFoodCategory.text = it.strCategory
                tvAllFoodArea.text = it.strArea
                ivAllFood.loadUrl(it.strMealThumb)
            }, listener)
        }
    }
}