package com.example.junemon.foodapi_mvvm.base

import androidx.lifecycle.LiveData
import com.example.junemon.foodapi_mvvm.data.local_data.LocalFoodData
import com.example.junemon.foodapi_mvvm.model.*

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

sealed class BaseState

data class OnGetLocalData(val data: LiveData<List<LocalFoodData>>) : BaseState()
data class OnShowRandomFood(val data: List<DetailFood.Meal>?) : BaseState()
data class OnShowDetailFoodData(val data: List<DetailFood.Meal>) : BaseState()
data class OnShowAllFood(val data: List<AllFood.Meal>?) : BaseState()
data class OnShowAreaFood(val data: List<AreaFood.Meal>?) : BaseState()
data class OnShowCategoryFood(val data: List<CategoryFood.Meal>?) : BaseState()
data class OnShowIngredientFood(val data: List<IngredientFood.Meal>?) : BaseState()
data class OnShowCategoryFoodDetail(val data: List<AllFoodCategoryDetail.Category>?) : BaseState()
data class OnShowFilterData(val data: List<FilterFood.Meal>) : BaseState()
data class OnComplete(val show: Boolean) : BaseState()
data class OnError(val msg: String?) : BaseState()