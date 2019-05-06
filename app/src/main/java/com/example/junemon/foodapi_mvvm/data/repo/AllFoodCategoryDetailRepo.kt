package com.example.junemon.foodapi_mvvm.data.repo

import com.example.junemon.foodapi_mvvm.api.ApiInterface
import com.example.junemon.foodapi_mvvm.model.AllFoodCategoryDetail
import io.reactivex.Observable

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class AllFoodCategoryDetailRepo(private val api: ApiInterface) {

    fun getAllCategoryDetailRepo(): Observable<List<AllFoodCategoryDetail.Category>> {
        return api.getAllFoodCategoryDetail().flatMapIterable {
            it.list
        }.map {
            return@map it
        }.toList().toObservable()
    }
}