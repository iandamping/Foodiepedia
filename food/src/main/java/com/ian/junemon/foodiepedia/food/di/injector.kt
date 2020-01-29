package com.ian.junemon.foodiepedia.food.di

import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.food.di.component.DaggerFoodComponent
import com.ian.junemon.foodiepedia.food.di.component.DaggerProfileComponent
import com.ian.junemon.foodiepedia.food.di.component.FoodComponent
import com.ian.junemon.foodiepedia.food.di.component.ProfileComponent
import com.ian.junemon.foodiepedia.ui.MainActivity

fun BaseFragment.sharedFoodComponent(): FoodComponent =
    DaggerFoodComponent.factory().create((this.activity as MainActivity).mainActivityComponent)

fun BaseFragment.sharedProfileComponent(): ProfileComponent =
    DaggerProfileComponent.factory().create((this.activity as MainActivity).mainActivityComponent)