package com.example.junemon.foodapi_mvvm.data.repo

import com.example.junemon.foodapi_mvvm.api.ApiInterface
import com.example.junemon.foodapi_mvvm.model.DetailFood
import io.reactivex.Observable

class DetailFoodRepo(private val api: ApiInterface) {
    fun getDetailFood(foodId: String): Observable<List<DetailFood.Meal>> {
        return api.getDetailedFood(foodId).flatMapIterable {
            it.food
        }.map {
            return@map it
        }.toList().toObservable()
    }
}