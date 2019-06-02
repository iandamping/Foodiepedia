package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnError
import com.example.junemon.foodapi_mvvm.base.OnShowCategoryFoodDetail
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodCategoryDetailRepo
import com.ian.app.helper.util.executes

class AllFoodCategoryViewModel(private val repo: AllFoodCategoryDetailRepo) : BaseViewModel() {

    fun getAllFoodCategoryDetail() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getAllCategoryDetailRepo(), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data ->
                liveDataState.value = OnComplete(true)
                liveDataState.value = OnShowCategoryFoodDetail(data)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}