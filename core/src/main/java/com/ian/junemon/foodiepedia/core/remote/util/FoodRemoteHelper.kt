package com.ian.junemon.foodiepedia.core.remote.util

import android.net.Uri
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 07,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRemoteHelper {

    suspend fun getFirebaseData(): Flow<DataHelper<List<FoodRemoteDomain>>>

    suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing>
}