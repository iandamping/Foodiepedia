package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnComplete
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.base.OnShowCategoryFoodDetail
import com.ian.junemon.foodiepedia.data.repo.AllFoodCategoryDetailRepo

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