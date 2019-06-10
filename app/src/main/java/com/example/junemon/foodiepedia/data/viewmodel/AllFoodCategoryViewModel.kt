package com.example.junemon.foodiepedia.data.viewmodel

import com.example.junemon.foodiepedia.base.BaseViewModel
import com.example.junemon.foodiepedia.base.OnComplete
import com.example.junemon.foodiepedia.base.OnError
import com.example.junemon.foodiepedia.base.OnShowCategoryFoodDetail
import com.example.junemon.foodiepedia.data.repo.AllFoodCategoryDetailRepo
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