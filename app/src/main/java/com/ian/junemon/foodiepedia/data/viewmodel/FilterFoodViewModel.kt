package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.FilterFoodRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class FilterFoodViewModel(private val repo: FilterFoodRepo, private val db: FoodDatabase) : BaseViewModel() {

    fun getIngredientFilterData(data: String) {
        compose.executes(repo.getIngredientFilterData(data), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.filterDao().insertFilterData(it.toDatabaseModel())

                }
            }
        })
    }

    fun getIngredientFilterArea(data: String) {
        compose.executes(repo.getIngredientFilterArea(data), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.filterDao().insertFilterData(it.toDatabaseModel())

                }
            }
        })
    }

    fun getIngredientFilterCategory(data: String) {
        compose.executes(repo.getIngredientFilterCategory(data), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.filterDao().insertFilterData(it.toDatabaseModel())

                }
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}