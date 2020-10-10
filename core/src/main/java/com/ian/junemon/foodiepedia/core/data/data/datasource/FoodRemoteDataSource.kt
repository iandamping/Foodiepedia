package com.ian.junemon.foodiepedia.core.data.data.datasource

import android.net.Uri
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRemoteDataSource {

    suspend fun getFirebaseData(): DataHelper<List<FoodRemoteDomain>>

    suspend fun getFirebaseDatas(): Flow<DataHelper<List<FoodRemoteDomain>>>

    suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing>
}

interface FoodCacheDataSource {

    fun getCache(): Flow<List<FoodCacheDomain>>

    fun getSavedDetailCache(): Flow<List<SavedFoodCacheDomain>>

    fun getCategorizeCache(foodCategory: String): Flow<List<FoodCacheDomain>>

    suspend fun setCache(vararg data: FoodCacheDomain)

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    suspend fun deleteSelectedId(selectedId: Int)

    fun loadSharedPreferenceFilter():String

    fun setSharedPreferenceFilter(data:String)
}