package com.ian.junemon.foodiepedia.feature.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.ian.junemon.foodiepedia.FoodApp
import com.ian.junemon.foodiepedia.feature.di.component.FoodComponent

fun Activity.activityComponent() = (application as FoodApp).provideActivityComponent()

fun Fragment.sharedFoodComponent(): FoodComponent =
    (this.activity?.application as FoodApp).provideFoodComponent()
