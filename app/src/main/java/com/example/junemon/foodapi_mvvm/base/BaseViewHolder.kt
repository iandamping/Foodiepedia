package com.example.junemon.foodapi_mvvm.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(override var containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    abstract fun bind(item: T, listener: (T) -> Unit)
}