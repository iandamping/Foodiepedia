package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.obsExecutes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnComplete
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.base.OnShowDetailFoodData
import com.ian.junemon.foodiepedia.data.repo.DetailFoodRepo

class DetailFoodViewModel(private val repo: DetailFoodRepo) : BaseViewModel() {

    fun setDetailFoodData(foodId: String) {
        liveDataState.value = OnComplete(false)
        compose.obsExecutes(repo.getDetailFood(foodId), {
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