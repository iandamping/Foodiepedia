package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodCategoryDetailRepo
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodRepo
import com.example.junemon.foodapi_mvvm.data.repo.RandomFoodRepo
import com.ian.app.helper.util.executes
import com.ian.app.helper.util.obsWithTripleZip

class AllFoodViewModel(
    private val allFoodRepo: AllFoodRepo,
    private val allFoodCategoryDetailRepo: AllFoodCategoryDetailRepo,
    private val randomFoodRepo: RandomFoodRepo
) : BaseViewModel() {

    fun getAllFoodData() {
        liveDataState.value = OnComplete(false)
        compose.executes(
            obsWithTripleZip(
                allFoodRepo.getCategoryFood(),
                allFoodCategoryDetailRepo.getAllCategoryDetailRepo(),
                randomFoodRepo.getRandomFood()
            )!!, {
                liveDataState.value = OnError(it?.localizedMessage)
                liveDataState.value = OnComplete(true)
            }, {
                if (it != null) {
                    liveDataState.value = OnComplete(true)
                    liveDataState.value = OnShowAllFood(it.first)
                    liveDataState.value = OnShowCategoryFoodDetail(it.second)
                    liveDataState.value = OnShowRandomFood(it.third)
                }
            })
    }


    override fun onCleared() {
        super.onCleared()
        dispose()
    }

}