package com.example.junemon.foodapi_mvvm.util

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.example.junemon.foodapi_mvvm.base.KotlinAdapter

fun <T> RecyclerView.setUp(
    items: List<T>,
    layoutResId: Int,
    bindHolder: View.(T) -> Unit,
    itemClick: T.() -> Unit = {},
    manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): KotlinAdapter<T> {

    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, {
        itemClick()
    }).apply {
        layoutManager = manager
        adapter = this
    }
}

fun <T> RecyclerView.setUpWithGrid(
    items: List<T>,
    layoutResId: Int,
    gridSize: Int,
    bindHolder: View.(T) -> Unit,
    itemClick: T.() -> Unit = {},
    manager: RecyclerView.LayoutManager = GridLayoutManager(this.context, gridSize)
): KotlinAdapter<T> {

    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, {
        itemClick()
    }).apply {
        layoutManager = manager
        adapter = this
    }
}

fun <T> RecyclerView.setUpWithSkid(
    items: List<T>,
    layoutResId: Int,
    bindHolder: View.(T) -> Unit,
    itemClick: (T) -> Unit,
    manager: RecyclerView.LayoutManager = SkidRightLayoutManager(1.5f, 0.85f)
): KotlinAdapter<T> {

    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, itemClick).apply {
        layoutManager = manager
        adapter = this
    }
}

fun <T> RecyclerView.setUpWithEchelon(
    items: List<T>,
    layoutResId: Int,
    bindHolder: View.(T) -> Unit,
    itemClick: (T) -> Unit,
    manager: RecyclerView.LayoutManager = EchelonLayoutManager(this.context)
): KotlinAdapter<T> {

    return KotlinAdapter(items, layoutResId, { bindHolder(it) }, itemClick).apply {
        layoutManager = manager
        adapter = this
    }
}