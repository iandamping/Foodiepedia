package com.ian.junemon.foodiepedia.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.model.Event
import com.ian.junemon.foodiepedia.model.EventObserver
import com.ian.junemon.foodiepedia.util.classes.RecyclerHorizontalSnapHelper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */

inline val Context.layoutInflater: LayoutInflater
    get() = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

 fun ViewGroup.inflates(layout: Int): View {
    return LayoutInflater.from(context).inflate(layout, this, false)
}

fun RecyclerView.horizontalRecyclerviewInitializer() {
    layoutManager = LinearLayoutManager(
        this.context, LinearLayoutManager.HORIZONTAL,
        false
    )
    if (this.onFlingListener == null) {
        RecyclerHorizontalSnapHelper()
            .attachToRecyclerView(this)
    }
}

fun RecyclerView.verticalRecyclerviewInitializer() {
    layoutManager = LinearLayoutManager(
        this.context, LinearLayoutManager.VERTICAL,
        false
    )
}

fun RecyclerView.gridRecyclerviewInitializer(size: Int) {
    layoutManager = GridLayoutManager(
        this.context, size
    )
}

fun generateRandomHexColor(): String {
    // create object of Random class
    val obj = Random()
    val randomNumber = obj.nextInt(0xffffff + 1)
    // format it as hexadecimal string and print

    return String.format("#%06x", randomNumber)
}


fun <T> Fragment.observeEvent(data: LiveData<Event<T>>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner, EventObserver {
        onBound.invoke(it)
    })
}

fun <T> Fragment.observe(data: LiveData<T>, onBound: ((T) -> Unit)) {
    data.observe(this.viewLifecycleOwner) {
        onBound.invoke(it)
    }
}

fun Fragment.clicks(view: View, duration: Long = 300, isUsingThrottle:Boolean = true,onBound: () -> Unit) {
    if (isUsingThrottle){
        view.clickListener().throttleFirst(duration).onEach {
            onBound.invoke()
        }.launchIn(this.viewLifecycleOwner.lifecycleScope)
    } else{
        view.setOnClickListener {
            onBound.invoke()
        }
    }

}

@FlowPreview
@ExperimentalCoroutinesApi
private fun View.clickListener(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        offer(Unit)
    }
    awaitClose { this@clickListener.setOnClickListener(null) }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit) {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}


fun Boolean.shimmerHandler(view:ShimmerFrameLayout){
    if (this){
        if (!view.isShimmerStarted && !view.isShimmerVisible) {
            view.startShimmer()
        }
    } else{
        if (view.isShimmerStarted && view.isShimmerVisible) {
            view.stopShimmer()
            view.hideShimmer()
            view.visibility = View.GONE
        }
    }
}

fun Fragment.getDrawables(@DrawableRes drawableId:Int):Drawable{
    return ContextCompat.getDrawable(
        requireContext(),
        drawableId
    )!!
}
