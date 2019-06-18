package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.executes
import com.ian.app.helper.util.obsWithTripleZip
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.AllFoodCategoryDetailRepo
import com.ian.junemon.foodiepedia.data.repo.AllFoodRepo
import com.ian.junemon.foodiepedia.data.repo.RandomFoodRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class AllFoodViewModel(
        private val allFoodRepo: AllFoodRepo,
        private val allFoodCategoryDetailRepo: AllFoodCategoryDetailRepo,
        private val randomFoodRepo: RandomFoodRepo, private val db: FoodDatabase
) : BaseViewModel() {

    fun getAllFoodData() {
        compose.executes(
                obsWithTripleZip(
                        allFoodRepo.getCategoryFood(),
                        allFoodCategoryDetailRepo.getAllCategoryDetailRepo(),
                        randomFoodRepo.getRandomFood()
                )!!, {
            liveDataState.value = OnError(it?.localizedMessage)
            getAllLocalFoodData()
            getAllLocalFoodCategoryDetail()
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.allFoodDao().insertAllFoodData(it.first?.toDatabaseModel())
                    db.allFoodCategoryDetailDao().insertAllFoodCategoryDetailData(it.second?.toDatabaseModel())
                }
                liveDataState.value = OnShowRandomFood(it.third)
            }
            getAllLocalFoodData()
            getAllLocalFoodCategoryDetail()
        })
    }

    private fun getAllLocalFoodData() {
        liveDataState.value = OnShowAllFood(db.allFoodDao().loadAllIngredientData())
    }

    private fun getAllLocalFoodCategoryDetail() {
        liveDataState.value = OnShowCategoryFoodDetail(db.allFoodCategoryDetailDao().loadAllIngredientData())
    }


    override fun onCleared() {
        super.onCleared()
        dispose()
    }

}