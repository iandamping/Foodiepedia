package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.net.Uri
import com.junemon.model.DataSourceHelper
import com.junemon.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow

interface FoodRemoteDataSource {

    suspend fun getFirebaseData(): Flow<DataSourceHelper<List<FoodRemoteDomain>>>

    suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing>
}
