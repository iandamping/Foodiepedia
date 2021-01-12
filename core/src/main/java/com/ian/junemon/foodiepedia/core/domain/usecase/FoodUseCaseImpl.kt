package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.util.DataConstant
import com.junemon.model.DataSourceHelper
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCaseImpl @Inject constructor(private val repo: FoodRepository) : FoodUseCase {

    override fun getCache(): Flow<Results<List<FoodCacheDomain>>> {
        return repo.getCache()
    }

    override fun getCategorizeCache(foodCategory: String): Flow<Results<List<FoodCacheDomain>>> {
        return repo.getCategorizeCache(foodCategory)
    }

    override fun getSavedDetailCache(): Flow<Results<List<SavedFoodCacheDomain>>> {
        return repo.getSavedDetailCache()
    }

    override fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> {
        return repo.uploadFirebaseData(data, imageUri)
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        repo.setCacheDetailFood(*data)
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        repo.deleteSelectedId(selectedId)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return repo.loadSharedPreferenceFilter()
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        repo.setSharedPreferenceFilter(data)
    }
}