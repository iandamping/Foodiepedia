package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.net.Uri
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow

interface FoodRemoteDataSource {

    fun getFirebaseData(): Flow<DataSourceHelper<List<FoodRemoteDomain>>>

    suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing>
}
