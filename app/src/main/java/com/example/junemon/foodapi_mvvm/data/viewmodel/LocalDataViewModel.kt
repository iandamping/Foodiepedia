package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnGetLocalData
import com.example.junemon.foodapi_mvvm.data.local_data.FoodDatabase
import com.example.junemon.foodapi_mvvm.data.local_data.LocalFoodData
import com.ian.app.helper.util.asyncRxExecutor

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