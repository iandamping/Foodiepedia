package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodListDataRepo
import com.example.junemon.foodapi_mvvm.util.executes

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo) : BaseViewModel() {

    //    fun getAllData() {
//        liveDataState.value = OnComplete(false)
//        compose.executes(
//            obsWithTripleZip(
//                repo.getAllFoodArea(),
//                repo.getAllFoodCategory(),
//                repo.getAllFoodIngredient()
//            ), {
//                liveDataState.value = OnComplete(true)
//                liveDataState.value = OnError(it.localizedMessage)
//            }, {
//                it?.let { data ->
//                    liveDataState.value = OnComplete(true)
//                    liveDataState.value = OnShowAreaFood(data.first)
//                    liveDataState.value = OnShowCategoryFood(data.second)
//                    liveDataState.value = OnShowIngredientFood(data.third)
//                }
//            })
//    }
    fun getIngredientData() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getAllFoodIngredient(), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it.localizedMessage)
        }, {
            liveDataState.value = OnComplete(true)
            it?.let { data -> liveDataState.value = OnShowIngredientFood(data) }
        })
    }

    fun getAreaData() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getAllFoodArea(), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it.localizedMessage)
        }, {
            liveDataState.value = OnComplete(true)
            it?.let { data -> liveDataState.value = OnShowAreaFood(data) }
        })
    }

    fun getCategoryData() {
        liveDataState.value = OnComplete(false)
        compose.executes(repo.getAllFoodCategory(), {
            liveDataState.value = OnComplete(true)
            liveDataState.value = OnError(it.localizedMessage)
        }, {
            liveDataState.value = OnComplete(true)
            it?.let { data -> liveDataState.value = OnShowCategoryFood(data) }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}