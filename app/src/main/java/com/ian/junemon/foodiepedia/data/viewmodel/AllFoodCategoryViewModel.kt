package com.ian.junemon.foodiepedia.data.viewmodel

import com.ian.app.helper.util.asyncRxExecutor
import com.ian.app.helper.util.executes
import com.ian.junemon.foodiepedia.base.BaseViewModel
import com.ian.junemon.foodiepedia.base.OnError
import com.ian.junemon.foodiepedia.data.local_data.FoodDatabase
import com.ian.junemon.foodiepedia.data.repo.AllFoodCategoryDetailRepo
import com.ian.junemon.foodiepedia.model.toDatabaseModel

class AllFoodCategoryViewModel(private val repo: AllFoodCategoryDetailRepo, private val db: FoodDatabase) : BaseViewModel() {

    fun getAllFoodCategoryDetail() {
        compose.executes(repo.getAllCategoryDetailRepo(), {
            liveDataState.value = OnError(it?.localizedMessage)
        }, {
            if (it != null) {
                compose.asyncRxExecutor {
                    db.allFoodCategoryDetailDao().insertAllFoodCategoryDetailData(it.toDatabaseModel())

                }
            }

        })
    }

    override fun onCleared() {
        super.onCleared()
        dispose()
    }
}