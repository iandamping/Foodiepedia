package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.repo.AllFoodListDataRepo

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
        compose.executes(repo.getAllFoodIngredient(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data -> liveDataState.value = OnShowIngredientFood(data) }
        })
    }

    fun getAreaData() {
        compose.executes(repo.getAllFoodArea(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data -> liveDataState.value = OnShowAreaFood(data) }
        })
    }

    fun getCategoryData() {
        compose.executes(repo.getAllFoodCategory(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            it?.let { data -> liveDataState.value = OnShowCategoryFood(data) }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}