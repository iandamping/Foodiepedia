package com.ian.junemon.foodiepedia.food.vm

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModel @Inject constructor(private val repository:FoodUseCase):ViewModel() {

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repository.getCache()

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): Flow<FirebaseResult<Nothing>> = repository.uploadFirebaseData(data, imageUri)
}