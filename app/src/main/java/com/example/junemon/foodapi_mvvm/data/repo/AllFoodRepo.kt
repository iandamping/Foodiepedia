package com.example.junemon.foodapi_mvvm.data.repo

import com.example.junemon.foodapi_mvvm.api.ApiInterface
import com.example.junemon.foodapi_mvvm.model.AllFood
import io.reactivex.Observable

class AllFoodRepo(private val api: ApiInterface) {

    fun getCategoryFood(): Observable<List<AllFood.Meal>> {
        return api.getAllFood().flatMapIterable {
            it.food
        }.map {
            return@map it
        }.toList().toObservable()

    }
}