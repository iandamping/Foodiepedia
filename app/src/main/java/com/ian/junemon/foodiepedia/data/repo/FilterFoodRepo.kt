package com.ian.junemon.foodiepedia.data.repo

import com.ian.junemon.foodiepedia.api.ApiInterface
import com.ian.junemon.foodiepedia.model.FilterFood
import io.reactivex.Observable

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class FilterFoodRepo(private val api: ApiInterface) {

    fun getIngredientFilterData(data: String): Observable<List<FilterFood.Meal>> {
        return api.getFilterByIngredient(data).flatMapIterable {
            it.allFilter
        }.map {
            return@map it
        }.toList().toObservable()
    }

    fun getIngredientFilterArea(data: String): Observable<List<FilterFood.Meal>> {
        return api.getFilterByArea(data).flatMapIterable {
            it.allFilter
        }.map {
            return@map it
        }.toList().toObservable()
    }

    fun getIngredientFilterCategory(data: String): Observable<List<FilterFood.Meal>> {
        return api.getFilterByCategory(data).flatMapIterable {
            it.allFilter
        }.map {
            return@map it
        }.toList().toObservable()
    }
}