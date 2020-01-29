package com.ian.junemon.foodiepedia.core.data.data.datasource

import android.net.Uri
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRemoteDataSource {

    suspend fun getFirebaseData(): Flow<DataHelper<List<FoodRemoteDomain>>>

    suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing>
}

interface FoodCacheDataSource {

    fun getCache(): Flow<List<FoodCacheDomain>>

    fun getCategirizeCache(foodCategory:String):Flow<List<FoodCacheDomain>>

    suspend fun setCache(data: List<FoodCacheDomain>)
}