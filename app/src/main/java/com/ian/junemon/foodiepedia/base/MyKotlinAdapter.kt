package com.ian.junemon.foodiepedia.base

import android.view.View

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */
class MyKotlinAdapter<T>(
        data: List<T>,
        layout: Int,
        private val bindHolder: View.(T) -> Unit,
        private val itemClicks: T.() -> Unit
) : MyAbstractAdapter<T>(data, layout, itemClicks) {

    override fun onBindViewHolder(holder: MyAbstractViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.bindHolder(item)
        holder.itemView.setOnClickListener { itemClicks(item) }
    }
}