package com.ian.junemon.foodiepedia.core.presentation.util.interfaces

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.ian.junemon.foodiepedia.core.presentation.base.adapter.BaseKotlinListAdapter

interface RecyclerHelper {

    fun <T> RecyclerView.setUpVerticalListAdapter(
        items: List<T>?,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
    ): BaseKotlinListAdapter<T>

    fun <T> RecyclerView.setUpSkidAdapter(
        items: List<T>?,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = SkidRightLayoutManager(1.5f, 0.85f)
    ): BaseKotlinListAdapter<T>

    fun <T> RecyclerView.setUpVerticalGridAdapter(
        items: List<T>?,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        gridSize: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = GridLayoutManager(this.context, gridSize)
    ): BaseKotlinListAdapter<T>

    fun <T> RecyclerView.setUpHorizontalListAdapter(
        items: List<T>?,
        diffUtil: DiffUtil.ItemCallback<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = LinearLayoutManager(
            this.context, LinearLayoutManager.HORIZONTAL,
            false
        )
    ): BaseKotlinListAdapter<T>?
}