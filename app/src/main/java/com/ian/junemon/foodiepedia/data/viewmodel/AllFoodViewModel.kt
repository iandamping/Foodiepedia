package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.executes
import com.ian.app.helper.util.obsWithTripleZip
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.repo.AllFoodCategoryDetailRepo
import com.ian.junemon.foodiepedia.data.repo.AllFoodRepo
import com.ian.junemon.foodiepedia.data.repo.RandomFoodRepo

class AllFoodViewModel(
        private val allFoodRepo: AllFoodRepo,
        private val allFoodCategoryDetailRepo: AllFoodCategoryDetailRepo,
        private val randomFoodRepo: RandomFoodRepo
) : BaseViewModel() {

    fun getAllFoodData() {
        compose.executes(
                obsWithTripleZip(
                        allFoodRepo.getCategoryFood(),
                        allFoodCategoryDetailRepo.getAllCategoryDetailRepo(),
                        randomFoodRepo.getRandomFood()
                )!!, {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
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