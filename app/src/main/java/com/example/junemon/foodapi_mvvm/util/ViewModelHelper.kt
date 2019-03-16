package com.example.junemon.foodapi_mvvm.util


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/*
Created by ian 10/March/2019
 */
inline fun <reified T : ViewModel> FragmentActivity.viewModelHelperForActivity(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

//inline fun <reified T : ViewModel> FragmentActivity.withViewModel(body: T.() -> Unit): T {
//    val vm = viewModelHelperForActivity<T>()
//    vm.body()
//    return vm
//}

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




