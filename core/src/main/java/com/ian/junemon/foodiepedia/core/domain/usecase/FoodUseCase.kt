package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCase @Inject constructor(private val repo: FoodRepository) {

    fun homeFoodPrefetch():  LiveData<Results<List<FoodCacheDomain>>> = repo.homeFoodPrefetch()

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) = repo.setCacheDetailFood(*data)

    fun getSavedDetailCache() = repo.getSavedDetailCache()

    suspend fun deleteSelectedId(selectedId: Int) = repo.deleteSelectedId(selectedId)

    fun getCache(): LiveData<List<FoodCacheDomain>> = repo.getCache()

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>> = repo.uploadFirebaseData(data, imageUri)

    fun registerSharedPrefStringListener() = repo.registerSharedPrefStringListener()

    fun unregisterSharedPrefStringListener() = repo.unregisterSharedPrefStringListener()

    fun loadSharedPreferenceFilter(): LiveData<String?> = repo.loadSharedPreferenceFilter().asLiveData()

    fun setSharedPreferenceFilter(data:String) = repo.setSharedPreferenceFilter(data)
}