package com.ian.junemon.foodiepedia.feature.di

import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.feature.di.component.DaggerFoodComponent
import com.ian.junemon.foodiepedia.feature.di.component.FoodComponent
import com.ian.junemon.foodiepedia.ui.MainActivity

fun BaseFragment.sharedFoodComponent(): FoodComponent =
    DaggerFoodComponent.factory().create((this.activity as MainActivity).mainActivityComponent)
