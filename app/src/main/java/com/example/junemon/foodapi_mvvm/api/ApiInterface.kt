package com.example.junemon.foodapi_mvvm.api

import com.example.junemon.foodapi_mvvm.model.*
import com.example.junemon.foodapi_mvvm.util.Constant.detailFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodArea
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategory
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodIngredient
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET(getAllFood)
    fun getAllFood(): Observable<AllFood>

    @GET(getAllFoodCategoryDetail)
    fun getAllFoodCategoryDetail(): Observable<AllFoodCategoryDetail>

    @GET(detailFood)
    fun getDetailedFood(@Query("i") foodID: String): Observable<DetailFood>

    @GET(getAllFoodCategory)
    fun getAllFoodCategory(@Query("c") data: String): Observable<CategoryFood>

    @GET(getAllFoodArea)
    fun getAllFoodArea(@Query("a") foodName: String): Observable<AreaFood>

    @GET(getAllFoodIngredient)
    fun getAllFoodIngredient(@Query("i") foodName: String): Observable<IngredientFood>
}