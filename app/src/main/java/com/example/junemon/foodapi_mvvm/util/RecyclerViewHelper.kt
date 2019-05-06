package com.example.junemon.foodapi_mvvm.util

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dingmouren.layoutmanagergroup.echelon.EchelonLayoutManager
import com.dingmouren.layoutmanagergroup.skidright.SkidRightLayoutManager
import com.example.junemon.foodapi_mvvm.base.MyKotlinAdapter

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

fun <T> RecyclerView.setUp(
        items: List<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: T.() -> Unit = {},
        manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)
): MyKotlinAdapter<T> {

    return MyKotlinAdapter(items, layoutResId, { bindHolder(it) }, {
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
): MyKotlinAdapter<T> {

    return MyKotlinAdapter(items, layoutResId, { bindHolder(it) }, {
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
        itemClick: (T) -> Unit = {},
        manager: RecyclerView.LayoutManager = SkidRightLayoutManager(1.5f, 0.85f)
): MyKotlinAdapter<T> {

    return MyKotlinAdapter(items, layoutResId, { bindHolder(it) }, itemClick).apply {
        layoutManager = manager
        adapter = this
    }
}

fun <T> RecyclerView.setUpWithEchelon(
        items: List<T>,
        layoutResId: Int,
        bindHolder: View.(T) -> Unit,
        itemClick: (T) -> Unit = {},
        manager: RecyclerView.LayoutManager = EchelonLayoutManager(this.context)
): MyKotlinAdapter<T> {

    return MyKotlinAdapter(items, layoutResId, { bindHolder(it) }, itemClick).apply {
        layoutManager = manager
        adapter = this
    }
}