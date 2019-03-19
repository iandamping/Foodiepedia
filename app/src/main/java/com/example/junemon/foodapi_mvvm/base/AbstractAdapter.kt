package com.example.junemon.foodapi_mvvm.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.junemon.foodapi_mvvm.util.inflates
import kotlinx.android.extensions.LayoutContainer

abstract class AbstractAdapter<T> constructor(protected var data: List<T>, private val layout: Int) :
    RecyclerView.Adapter<AbstractAdapter.AbstractHolder>() {

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder {
        return AbstractHolder(parent.inflates(layout)).apply {
            this.itemView.setOnClickListener {
                if (this.adapterPosition != RecyclerView.NO_POSITION) {
                    onItemClick(this.itemView, this.adapterPosition)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        val item = data[position]
        holder.itemView.bind(item)
    }

    protected open fun View.bind(item: T) {

    }

    protected open fun onItemClick(itemView: View, position: Int) {
    }

    class AbstractHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer
}