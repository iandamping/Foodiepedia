package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.AllFoodListDataRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo, private val db: FoodDatabase) : BaseViewModel() {

    fun getIngredientData() {
        compose.executes(repo.getAllFoodIngredient(), {
            liveDataState.value = OnError(it?.localizedMessage)
            getLocalIngredientData()
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.ingredientDao().insertIngredientData(it.toDatabaseModel())
                }
            }
            getLocalIngredientData()
        })
    }


    fun getAreaData() {
        compose.executes(repo.getAllFoodArea(), {
            liveDataState.value = OnError(it?.localizedMessage)
            getLocalAreaData()
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.areaDao().insertAreaData(it.toDatabaseModel())
                }
            }
            getLocalAreaData()

        })
    }

    fun getCategoryData() {
        compose.executes(repo.getAllFoodCategory(), {
            liveDataState.value = OnError(it?.localizedMessage)
            getLocalCategoryData()

        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.categoryDao().insertCategoryData(it.toDatabaseModel())
                }
            }
            getLocalCategoryData()


        })
    }

    private fun getLocalIngredientData() {
        liveDataState.value = OnShowIngredientFood(db.ingredientDao().loadAllIngredientData())
    }

    private fun getLocalAreaData() {
        liveDataState.value = OnShowAreaFood(db.areaDao().loadAllIngredientData())
    }

    private fun getLocalCategoryData() {
        liveDataState.value = OnShowCategoryFood(db.categoryDao().loadAllIngredientData())
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}