package com.example.junemon.foodapi_mvvm.ui.allfood

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.model.AllFood
import com.example.junemon.foodapi_mvvm.util.inflates
import com.example.junemon.foodapi_mvvm.util.loadUrl
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_all_food.*

class AllFoodAdapter(var data: List<AllFood.Meal>, var listener: (AllFood.Meal) -> Unit) :
    RecyclerView.Adapter<AllFoodAdapter.AllFoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFoodViewHolder {
        return AllFoodViewHolder(parent.inflates(R.layout.item_all_food))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: AllFoodViewHolder, position: Int) {
        holder.bind(data.get(position), listener)
    }


    class AllFoodViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(data: AllFood.Meal, listener: (AllFood.Meal) -> Unit) {
            tvAllFoodName.text = data.strMeal
            tvAllFoodCategory.text = data.strCategory
            tvAllFoodArea.text = data.strArea
            ivAllFood.loadUrl(data.strMealThumb)
            itemView.setOnClickListener { listener(data) }
        }
    }
}