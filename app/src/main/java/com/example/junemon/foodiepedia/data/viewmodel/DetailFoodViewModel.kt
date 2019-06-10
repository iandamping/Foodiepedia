package com.example.junemon.foodiepedia.data.viewmodel

import com.example.junemon.foodiepedia.base.BaseViewModel
import com.example.junemon.foodiepedia.base.OnComplete
import com.example.junemon.foodiepedia.base.OnError
import com.example.junemon.foodiepedia.base.OnShowDetailFoodData
import com.example.junemon.foodiepedia.data.repo.DetailFoodRepo
import com.ian.app.helper.util.executes

class DetailFoodViewModel(private val repo: DetailFoodRepo) : BaseViewModel() {

    fun setDetailFoodData(foodId: String) {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getDetailFood(foodId), {
            liveDataState.value = OnError(it?.localizedMessage)
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