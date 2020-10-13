package com.ian.junemon.foodiepedia.core.domain.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface FoodRepository {

    fun homeFoodPrefetch(): LiveData<Results<List<FoodCacheDomain>>>

    fun getCache(): LiveData<List<FoodCacheDomain>>

    fun getSavedDetailCache(): LiveData<List<SavedFoodCacheDomain>>

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>>

    suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain)

    suspend fun deleteSelectedId(selectedId: Int)

    fun registerSharedPrefStringListener()

    fun unregisterSharedPrefStringListener()

    fun loadSharedPreferenceFilter():Flow<String?>

    fun setSharedPreferenceFilter(data:String)
}