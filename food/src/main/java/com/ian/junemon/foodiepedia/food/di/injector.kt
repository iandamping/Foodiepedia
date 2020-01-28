package com.ian.junemon.foodiepedia.food.di

import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.ui.MainActivity

fun BaseFragment.sharedFoodComponent(): FoodComponent =
    DaggerFoodComponent.factory().create((this.activity as MainActivity).mainActivityComponent)