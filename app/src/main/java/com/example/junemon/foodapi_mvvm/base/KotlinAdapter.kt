package com.example.junemon.foodapi_mvvm.base

import android.view.View

class KotlinAdapter<T>(data: List<T>, layout: Int, private val bindHolder: View.(T) -> Unit) :
    AbstractAdapter<T>(data, layout) {

    constructor(data: List<T>, layout: Int, bindHolder: View.(T) -> Unit, itemClick: T.() -> Unit = {}) : this(
        data,
        layout,
        bindHolder
    ) {
        this.itemClick = itemClick
    }

    private var itemClick: T.() -> Unit = {}

    override fun onBindViewHolder(holder: AbstractHolder, position: Int) {
        holder.itemView.bindHolder(data[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        data[position].itemClick()
    }
}