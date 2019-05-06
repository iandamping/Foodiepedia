package com.example.junemon.foodapi_mvvm.data.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnError
import com.example.junemon.foodapi_mvvm.base.OnShowAllFood
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodRepo
import com.example.junemon.foodapi_mvvm.util.executes

class AllFoodViewModel(private val repo: AllFoodRepo) : BaseViewModel() {

    fun getAllFoodData() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getCategoryFood(), {
            liveDataState.value = OnError(it.localizedMessage)
            liveDataState.value = OnComplete(true)
        }, {
            it?.let { data ->
                liveDataState.value = OnComplete(true)
                liveDataState.value = OnShowAllFood(data)
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        dispose()
    }

}