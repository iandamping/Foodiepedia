package com.ian.junemon.foodiepedia.ui.fragment.home

import com.ian.junemon.foodiepedia.base.BaseFragmentView
import com.ian.junemon.foodiepedia.data.local_data.all_food.LocalAllFoodData
import com.ian.junemon.foodiepedia.data.local_data.all_food_category_detail.LocalAllFoodCategoryDetailData
import com.ian.junemon.foodiepedia.model.DetailFood

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
interface HomeView : BaseFragmentView {
    fun onGetAllFood(data: List<LocalAllFoodData>?)
    fun onGetAllFoodCategoryDetails(data: List<LocalAllFoodCategoryDetailData>?)
    fun onGetRandomFood(data: DetailFood.Meal?)
    fun onFailGetAllFood()
    fun onFailGetAllFoodCategoryDetails()


}