package com.ian.junemon.foodiepedia.ui.fragment.home

import com.ian.junemon.foodiepedia.base.BaseFragmentView
import com.ian.junemon.foodiepedia.model.AllFood
import com.ian.junemon.foodiepedia.model.AllFoodCategoryDetail
import com.ian.junemon.foodiepedia.model.DetailFood

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
interface HomeView : BaseFragmentView {
    fun onGetAllFood(data: List<AllFood.Meal>?)
    fun onGetAllFoodCategoryDetails(data: List<AllFoodCategoryDetail.Category>?)
    fun onGetRandomFood(data: DetailFood.Meal?)
    fun onFailGetAllFood()
    fun onFailGetAllFoodCategoryDetails()


}