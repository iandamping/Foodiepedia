package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnError
import com.example.junemon.foodapi_mvvm.base.OnShowDetailFoodData
import com.example.junemon.foodapi_mvvm.data.repo.DetailFoodRepo
import com.example.junemon.foodapi_mvvm.util.executes

class DetailFoodViewModel(private val repo: DetailFoodRepo) : BaseViewModel() {

    fun setDetailFoodData(foodId: String) {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getDetailFood(foodId), {
            liveDataState.value = OnError(it.localizedMessage)
            liveDataState.value = OnComplete(true)
        }, {
            it?.let { data ->
                liveDataState.value = OnComplete(true)
                liveDataState.value = OnShowDetailFoodData(data)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }

}