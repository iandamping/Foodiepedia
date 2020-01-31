package com.ian.junemon.foodiepedia.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRepository {
    suspend fun foodPrefetch(): Flow<WorkerResult<Nothing>>

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>>

    fun getCategorizeCache(category: String): LiveData<List<FoodCacheDomain>>

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>>
}