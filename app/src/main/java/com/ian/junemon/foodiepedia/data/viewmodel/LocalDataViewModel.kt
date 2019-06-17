package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnGetLocalData
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.local_data.LocalFoodData

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class LocalDataViewModel(private val db: FoodDatabase) : BaseViewModel() {

    fun insertLocalData(data: LocalFoodData) {
        compose.asyncRxExecutor {
            db.foodDao().insertFoodData(data)
        }
    }

    fun updateLocalData(data: LocalFoodData) {
        compose.asyncRxExecutor {
            db.foodDao().updateFoodData(data)
        }
    }

    fun deleteAllLocalData() {
        compose.asyncRxExecutor {
            db.foodDao().deleteAllData()
        }
    }

    fun deleteSelectedLocalData(foodID: Int) {
        compose.asyncRxExecutor {
            db.foodDao().deleteSelectedId(foodID)
        }
    }

    fun getLocalData() {
        liveDataState.value = OnGetLocalData(db.foodDao().loadAllFoodData())
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}