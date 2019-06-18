package com.ian.junemon.foodiepedia.base

import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodData
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData
import com.ian.junemon.foodiepedia.data.local_data.area.LocalAreaData
import com.ian.junemon.foodiepedia.data.local_data.detail.LocalFoodData
import com.ian.junemon.foodiepedia.data.local_data.ingredient.LocalIngredientData
import com.ian.junemon.foodiepedia.model.DetailFood
import com.ian.junemon.foodiepedia.model.FilterFood

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

sealed class BaseState

data class OnGetLocalData(val data: LiveData<List<LocalFoodData>>) : BaseState()
data class OnShowRandomFood(val data: List<DetailFood.Meal>?) : BaseState()
data class OnShowDetailFoodData(val data: List<DetailFood.Meal>) : BaseState()
data class OnShowAllFood(val data: LiveData<List<LocalAllFoodData>>?) : BaseState()
data class OnShowAreaFood(val data: LiveData<List<LocalAreaData>>?) : BaseState()
data class OnShowIngredientFood(val data: LiveData<List<LocalIngredientData>>?) : BaseState()
data class OnShowCategoryFoodDetail(val data: LiveData<List<LocalAllFoodCategoryDetailData>>?) : BaseState()
data class OnShowFilterData(val data: List<FilterFood.Meal>) : BaseState()
data class OnComplete(val show: Boolean) : BaseState()
data class OnError(val msg: String?) : BaseState()