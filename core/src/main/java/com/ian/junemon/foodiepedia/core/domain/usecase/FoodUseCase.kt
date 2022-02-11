package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.model.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 03,January,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodUseCase {

    fun getCache(): Flow<RepositoryData<List<FoodCacheDomain>>>

    fun getCacheById(id:Int): Flow<RepositoryData<FoodCacheDomain>>

    fun prefetchData():  Flow<RepositoryData<List<FoodCacheDomain>>>

    suspend fun setCache(vararg data: FoodCacheDomain)

    fun getCategorizeCache(foodCategory: String): Flow<RepositoryData<List<FoodCacheDomain>>>

    fun getSavedDetailCache(): Flow<RepositoryData<List<SavedFoodCacheDomain>>>

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>>

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    suspend fun deleteSelectedId(selectedId: Int)

    fun loadSharedPreferenceFilter(): Flow<String>

    suspend fun setSharedPreferenceFilter(data: String)

    suspend fun deleteFood()
}