package com.ian.junemon.foodiepedia.data.datasource.remote

import android.net.Uri
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ian Damping on 05,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeFoodRemoteDataSourceImpl(
    var listOfFakeFood: List<FoodRemoteDomain>?
) : FoodRemoteDataSource {

    override suspend fun getFirebaseData(): Flow<DataSourceHelper<List<FoodRemoteDomain>>> {
        return if (listOfFakeFood.isNullOrEmpty()) {
            flowOf(DataSourceHelper.DataSourceError(NullPointerException()))
        } else {
            flowOf(DataSourceHelper.DataSourceValue(listOfFakeFood!!))
        }
    }

    override suspend fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): FirebaseResult<Nothing> {
        // because Uri is android component so it cannot used in test
        return FirebaseResult.SuccessPush
    }
}