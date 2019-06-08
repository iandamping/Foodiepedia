package com.example.junemon.foodapi_mvvm.util


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.koin.android.viewmodel.ext.android.getViewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

inline fun <reified T : ViewModel> FragmentActivity.viewModelHelperForActivity(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

inline fun <reified T : ViewModel> FragmentActivity.withViewModel(crossinline factory: () -> T, body: T.() -> Unit): T {
    val vm = getViewModel(factory)
    vm.body()
    return vm
}


inline fun <reified T : ViewModel> Fragment.getViewModel(crossinline factory: () -> T): T {
    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }
    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}

inline fun <reified T : ViewModel> Fragment.withViewModel(crossinline factory: () -> T, body: T.() -> Unit): T {
    val vm = getViewModel(factory)
    vm.body()
    return vm
}


inline fun <reified T : ViewModel> FragmentActivity.initPresenter(crossinline factory: () -> T): T {
    return getViewModel(factory)
}

inline fun <reified T : ViewModel> Fragment.initPresenter(crossinline factory: () -> T): T {
    return getViewModel(factory)
}


