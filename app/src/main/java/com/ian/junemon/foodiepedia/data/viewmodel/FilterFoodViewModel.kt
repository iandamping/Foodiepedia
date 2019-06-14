package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnComplete
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.base.OnShowFilterData
import com.ian.junemon.foodiepedia.data.repo.FilterFoodRepo
import com.ian.app.helper.util.executes

class FilterFoodViewModel(private val repo: FilterFoodRepo) : BaseViewModel() {
    fun getIngredientFilterData(data: String) {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getIngredientFilterData(data), {
            liveDataState.value = OnError(it?.localizedMessage)
            liveDataState.value = OnComplete(true)
        }, {
            it?.let { data ->
                liveDataState.value = OnShowFilterData(data)
                liveDataState.value = OnComplete(true)
            }
        })
    }

    fun getIngredientFilterArea(data: String) {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getIngredientFilterArea(data), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data ->
                liveDataState.value = OnShowFilterData(data)
                liveDataState.value = OnComplete(true)
            }
        })
    }

    fun getIngredientFilterCategory(data: String) {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getIngredientFilterCategory(data), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data ->
                liveDataState.value = OnShowFilterData(data)
                liveDataState.value = OnComplete(true)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}