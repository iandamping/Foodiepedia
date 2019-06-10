package com.example.junemon.foodiepedia.data.repo

import com.example.junemon.foodiepedia.api.ApiInterface
import com.example.junemon.foodiepedia.model.DetailFood
import io.reactivex.Observable

/**
 *
Created by Ian Damping on 28/05/2019.
Github = https://github.com/iandamping
 */
class RandomFoodRepo(private val api: ApiInterface) {

    fun getRandomFood(): Observable<List<DetailFood.Meal>> {
        return api.getRandomFood().flatMapIterable {
            it.food
        }.map { return@map it }.toList().toObservable()
    }
}