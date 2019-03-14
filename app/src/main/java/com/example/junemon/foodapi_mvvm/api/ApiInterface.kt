package com.example.junemon.foodapi_mvvm.api

import com.example.junemon.foodapi_mvvm.model.*
import com.example.junemon.foodapi_mvvm.util.Constant.detailFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFood
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodArea
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategory
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodCategoryDetail
import com.example.junemon.foodapi_mvvm.util.Constant.getAllFoodIngredient
import com.example.junemon.foodapi_mvvm.util.Constant.searchFoodByName
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

    @GET(searchFoodByName)
    fun getDetailedFoodByName(@Query("s") foodName: String): Observable<DetailFood>

    @GET(getAllFoodCategory)
    fun getAlLFoodCategory(@Query("c") data: String): Observable<CategoryFood>

    @GET(getAllFoodArea)
    fun getAlLFoodArea(@Query("a") foodName: String): Observable<AreaFood>

    @GET(getAllFoodIngredient)
    fun getAlLFoodIngredient(@Query("i") foodName: String): Observable<AreaFood>
}