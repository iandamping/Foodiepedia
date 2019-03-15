package com.example.junemon.foodapi_mvvm.data.repo

import com.example.junemon.foodapi_mvvm.api.ApiInterface
import com.example.junemon.foodapi_mvvm.model.AreaFood
import com.example.junemon.foodapi_mvvm.model.CategoryFood
import com.example.junemon.foodapi_mvvm.model.IngredientFood
import com.example.junemon.foodapi_mvvm.util.Constant.allCategoryValue
import io.reactivex.Observable

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
            it.allCategory
        }.map {
            return@map it
        }.toList().toObservable()
    }
}