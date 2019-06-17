package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.AllFoodListDataRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo, private val db: FoodDatabase) : BaseViewModel() {

    fun getIngredientData() {
        compose.executes(repo.getAllFoodIngredient(), {
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
        compose.executes(repo.getAllFoodArea(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            compose.asyncRxExecutor {
                if (it != null) {
                    db.areaDao().insertAreaData(it.toDatabaseModel())
                }

            }
        })
    }

    fun getCategoryData() {
        compose.executes(repo.getAllFoodCategory(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            compose.asyncRxExecutor {
                if (it != null) {
                    db.categoryDao().insertCategoryData(it.toDatabaseModel())
                }
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}