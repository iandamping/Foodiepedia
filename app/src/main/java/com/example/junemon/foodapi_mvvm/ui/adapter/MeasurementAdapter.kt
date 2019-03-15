package com.example.junemon.foodapi_mvvm.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.R
import com.example.junemon.foodapi_mvvm.util.inflates
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_measurement_adapter.*

class MeasurementAdapter(var data: List<String>) : RecyclerView.Adapter<MeasurementAdapter.MeasurementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeasurementViewHolder {
        return MeasurementViewHolder(parent.inflates(R.layout.item_measurement_adapter))
    }

    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: MeasurementViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class MeasurementViewHolder(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(data: String) {
            tvMeasurementAdapter.text = data
        }
    }
}