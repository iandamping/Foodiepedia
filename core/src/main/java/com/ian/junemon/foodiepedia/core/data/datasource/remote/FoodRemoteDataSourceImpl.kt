package com.ian.junemon.foodiepedia.core.data.datasource.remote

import android.net.Uri
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.remote.util.FoodRemoteHelper
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRemoteDataSourceImpl @Inject constructor(private val foodRemoteHelper: FoodRemoteHelper) : FoodRemoteDataSource {
    override suspend fun getFirebaseData(): DataHelper<List<FoodRemoteDomain>> {
        return foodRemoteHelper.getFirebaseData()
    }

    override suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing> {
       return foodRemoteHelper.uploadFirebaseData(data, imageUri)
    }
}