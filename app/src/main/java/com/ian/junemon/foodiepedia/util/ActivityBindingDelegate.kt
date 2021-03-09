package com.ian.junemon.foodiepedia.util

import android.os.Looper
import android.view.LayoutInflater
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * Created by Ian Damping on 14,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ActivityBindingDelegate<T : ViewBinding>(
    private val activity: AppCompatActivity,
    private val viewInflater:(LayoutInflater) -> T
) :ReadOnlyProperty<AppCompatActivity,T>, LifecycleObserver{

    private var _bindingValue: T? = null

    init {
        activity.lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @Suppress("Unused")
    fun onCreate() {
        if (_bindingValue == null) {
            _bindingValue = viewInflater(activity.layoutInflater)
        }
        _bindingValue?.let { nonNullBind ->
            with(activity){
                setContentView(nonNullBind.root)
                lifecycle.removeObserver(this@ActivityBindingDelegate)
            }
        }

    }


    @MainThread
    override fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        if (_bindingValue == null) {

            // This must be on the main thread only
            if (Looper.myLooper() != Looper.getMainLooper()) {
                throw IllegalThreadStateException("This cannot be called from other threads. It should be on the main thread only.")
            }

            _bindingValue = viewInflater(thisRef.layoutInflater)
        }
        return _bindingValue!!
    }
}

inline fun <reified T : ViewBinding> AppCompatActivity.activityViewBinding(noinline initializer: (LayoutInflater) -> T) =
    ActivityBindingDelegate(this, initializer)