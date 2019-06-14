package com.ian.junemon.foodiepedia.data.repo

import com.ian.junemon.foodiepedia.api.ApiInterface
import com.ian.junemon.foodiepedia.model.AllFood
import io.reactivex.Observable

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class AllFoodRepo(private val api: ApiInterface) {

    fun getCategoryFood(): Observable<List<AllFood.Meal>> {
        return api.getAllFood().flatMapIterable {
            it.food
        }.map {
            return@map it
        }.toList().toObservable()

    }
}