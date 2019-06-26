package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.obsExecutes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.base.OnShowAreaFood
import com.ian.junemon.foodiepedia.base.OnShowIngredientFood
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.AllFoodListDataRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo, private val db: FoodDatabase) : BaseViewModel() {

    fun getIngredientData() {
        compose.obsExecutes(repo.getAllFoodIngredient(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.ingredientDao().insertIngredientData(it.toDatabaseModel())
                }
            }
        })
    }


    fun getAreaData() {
        compose.obsExecutes(repo.getAllFoodArea(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.areaDao().insertAreaData(it.toDatabaseModel())
                }
            }

        })
    }


    fun getLocalIngredientData() {
        liveDataState.value = OnShowIngredientFood(db.ingredientDao().loadAllIngredientData())
    }

    fun getLocalAreaData() {
        liveDataState.value = OnShowAreaFood(db.areaDao().loadAllIngredientData())
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}