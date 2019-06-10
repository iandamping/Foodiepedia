package com.example.junemon.foodiepedia.ui.fragment.home

import com.example.junemon.foodiepedia.base.BaseFragmentView
import com.example.junemon.foodiepedia.model.AllFood
import com.example.junemon.foodiepedia.model.AllFoodCategoryDetail
import com.example.junemon.foodiepedia.model.DetailFood

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