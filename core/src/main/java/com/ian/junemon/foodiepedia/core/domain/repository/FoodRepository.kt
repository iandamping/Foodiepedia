package com.ian.junemon.foodiepedia.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRepository {

    fun prefetchData():  Flow<RepositoryData<List<FoodCacheDomain>>>

    fun getCache(): Flow<RepositoryData<List<FoodCacheDomain>>>

    fun getCacheById(id:Int): Flow<RepositoryData<FoodCacheDomain>>

    fun getCategorizeCache(foodCategory: String): Flow<RepositoryData<List<FoodCacheDomain>>>

    fun getSavedDetailCache(): Flow<RepositoryData<List<SavedFoodCacheDomain>>>

    fun getFoodOfTheDay(): Flow<RepositoryData<List<FoodCacheDomain>>>

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>>

    fun setCache(vararg data: FoodCacheDomain)

    fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    fun deleteSelectedId(selectedId: Int)

    fun loadSharedPreferenceFilter():Flow<String>

    fun setSharedPreferenceFilter(data:String)

    fun deleteFood()
}