package com.ian.junemon.foodiepedia.data.repo

import com.ian.junemon.foodiepedia.api.ApiInterface
import com.ian.junemon.foodiepedia.model.AreaFood
import com.ian.junemon.foodiepedia.model.CategoryFood
import com.ian.junemon.foodiepedia.model.IngredientFood
import com.ian.junemon.foodiepedia.util.Constant.allCategoryValue
import io.reactivex.Observable

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class AllFoodListDataRepo(private val api: ApiInterface) {

    fun getAllFoodCategory(): Observable<List<CategoryFood.Meal>> {
        return api.getAllFoodCategory(allCategoryValue).flatMapIterable {
            it.allCategory
        }.map {
            return@map it
        }.toList().toObservable()
    }

    fun getAllFoodArea(): Observable<List<AreaFood.Meal>> {
        return api.getAllFoodArea(allCategoryValue).flatMapIterable {
            it.allCategory
        }.map {
            return@map it
        }.toList().toObservable()
    }

    fun getAllFoodIngredient(): Observable<List<IngredientFood.Meal>> {
        return api.getAllFoodIngredient(allCategoryValue).flatMapIterable {
            it.allIngredientData
        }.map {
            return@map it
        }.toList().toObservable()
    }
}