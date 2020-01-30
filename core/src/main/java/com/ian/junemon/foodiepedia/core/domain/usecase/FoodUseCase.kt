package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCase @Inject constructor(private val repo: FoodRepository) {

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repo.getCache()

    fun getCategorizeCache(category: String): LiveData<List<FoodCacheDomain>> = repo.getCategorizeCache(category)

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>> = repo.uploadFirebaseData(data, imageUri)
}