package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodListDataRepo
import com.example.junemon.foodapi_mvvm.util.executes
import com.example.junemon.foodapi_mvvm.util.obsWithTripleZip

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo) : BaseViewModel() {

    fun getAllData() {
        liveDataState.value = OnComplete(false)
        compose.executes(
            obsWithTripleZip(
                repo.getAllFoodArea(),
                repo.getAllFoodCategory(),
                repo.getAllFoodIngredient()
            ), {
                liveDataState.value = OnComplete(true)
                liveDataState.value = OnError(it.localizedMessage)
            }, {
                it?.let { data ->
                    liveDataState.value = OnComplete(true)
                    liveDataState.value = OnShowAreaFood(data.first)
                    liveDataState.value = OnShowCategoryFood(data.second)
                    liveDataState.value = OnShowIngredientFood(data.third)
                }
            })
    }
}