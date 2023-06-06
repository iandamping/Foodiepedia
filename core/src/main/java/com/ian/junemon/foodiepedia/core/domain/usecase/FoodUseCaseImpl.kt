package com.ian.junemon.foodiepedia.core.domain.usecase

import android.net.Uri
import androidx.lifecycle.LiveData
import com.ian.junemon.foodiepedia.core.domain.model.*
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCaseImpl @Inject constructor(private val repo: FoodRepository) : FoodUseCase {
    override fun getCache(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return repo.getCache()
    }

    override fun getCacheById(id: Int): Flow<RepositoryData<FoodCacheDomain>> {
        return repo.getCacheById(id)
    }

    override fun prefetchData():  Flow<RepositoryData<List<FoodCacheDomain>>> {
        return repo.prefetchData()
    }

    override fun setCache(vararg data: FoodCacheDomain) {
        repo.setCache(*data)
    }

    override fun getCategorizeCache(foodCategory: String): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return repo.getCategorizeCache(foodCategory)
    }

    override fun getSavedDetailCache(): Flow<RepositoryData<List<SavedFoodCacheDomain>>> {
        return repo.getSavedDetailCache()
    }

    override fun getFoodOfTheDay(): Flow<RepositoryData<List<FoodCacheDomain>>> {
        return repo.getFoodOfTheDay()
    }

    override fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> {
        return repo.uploadFirebaseData(data, imageUri)
    }

    override fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        repo.setCacheDetailFood(*data)
    }

    override fun deleteSelectedId(selectedId: Int) {
        repo.deleteSelectedId(selectedId)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return repo.loadSharedPreferenceFilter()
    }

    override fun setSharedPreferenceFilter(data: String) {
        repo.setSharedPreferenceFilter(data)
    }

    override fun deleteFood() {
        repo.deleteFood()
    }
}