package com.example.junemon.foodapi_mvvm.api

import com.example.junemon.foodapi_mvvm.model.*
import com.example.junemon.foodapi_mvvm.util.Constant.detailFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodArea
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategory
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodIngredient
import com.example.junemon.foodapi_mvvm.util.Constant.getFilterData
import com.example.junemon.foodapi_mvvm.util.Constant.getRandomFood
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

interface ApiInterface {
    @GET(getAllFood)
    fun getAllFood(): Observable<AllFood>

    @GET(getAllFoodCategoryDetail)
    fun getAllFoodCategoryDetail(): Observable<AllFoodCategoryDetail>

    @GET(detailFood)
    fun getDetailedFood(@Query("i") foodID: String): Observable<DetailFood>

    @GET(getRandomFood)
    fun getRandomFood(): Observable<DetailFood>

    @GET(getAllFoodCategory)
    fun getAllFoodCategory(@Query("c") data: String): Observable<CategoryFood>

    @GET(getAllFoodArea)
    fun getAllFoodArea(@Query("a") foodName: String): Observable<AreaFood>

    @GET(getAllFoodIngredient)
    fun getAllFoodIngredient(@Query("i") foodName: String): Observable<IngredientFood>

    @GET(getFilterData)
    fun getFilterByIngredient(@Query("i") ingredientData: String): Observable<FilterFood>

    @GET(getFilterData)
    fun getFilterByCategory(@Query("c") ingredientData: String): Observable<FilterFood>

    @GET(getFilterData)
    fun getFilterByArea(@Query("a") ingredientData: String): Observable<FilterFood>
}