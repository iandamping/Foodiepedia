package com.example.junemon.foodapi_mvvm.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ian.app.helper.util.inflates
import kotlinx.android.extensions.LayoutContainer

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */
abstract class MyAbstractAdapter<T>(
    protected var data: List<T>,
    private val layout: Int,
    private val clickListener: (T) -> Unit
) :
    RecyclerView.Adapter<MyAbstractAdapter.MyAbstractViewHolder>() {

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAbstractViewHolder {
        return MyAbstractViewHolder(parent.inflates(layout))
    }

    override fun onBindViewHolder(holder: MyAbstractViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.bind(item)
        holder.itemView.setOnClickListener { clickListener(item) }
    }

    protected open fun View.bind(item: T) {
    }

    class MyAbstractViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer
}