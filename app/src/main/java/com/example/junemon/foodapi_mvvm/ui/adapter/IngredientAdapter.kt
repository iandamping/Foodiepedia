package com.example.junemon.foodapi_mvvm.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.util.inflates
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_ingredient_adapter.*

class IngredientAdapter(var data: List<String>) : RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder(parent.inflates(R.layout.item_ingredient_adapter))
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class IngredientViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer {
        fun bind(data: String) {
            tvIngredientAdapter.text = data
        }
    }
}