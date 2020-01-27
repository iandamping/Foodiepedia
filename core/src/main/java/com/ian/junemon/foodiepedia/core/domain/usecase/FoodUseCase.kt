package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.DataHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCase @Inject constructor(private val repo:FoodRepository) {

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repo.getCache()

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): Flow<FirebaseResult<Nothing>> = repo.uploadFirebaseData(data, imageUri)
}