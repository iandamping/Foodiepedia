package com.example.junemon.foodapi_mvvm.data.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowCategoryFoodDetail
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodCategoryDetailRepo
import com.example.junemon.foodapi_mvvm.util.executes

class AllFoodCategoryViewModel(private val repo: AllFoodCategoryDetailRepo) : BaseViewModel() {

    fun getAllFoodCategoryDetail() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getAllCategoryDetailRepo(), {
            liveDataState.value = OnComplete(true)
        }, {
            it?.let { data ->
                liveDataState.value = OnComplete(true)
                liveDataState.value = OnShowCategoryFoodDetail(data)
            }
        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        dispose()
    }
}