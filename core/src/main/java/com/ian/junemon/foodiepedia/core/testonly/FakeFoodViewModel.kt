package com.ian.junemon.foodiepedia.core.testonly

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import org.jetbrains.annotations.TestOnly

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@TestOnly
@VisibleForTesting
internal class FakeFoodViewModel(private val repository: FoodRepository) : ViewModel() {

    fun getCategorizeCache(category: String): LiveData<List<FoodCacheDomain>> =
        repository.getCategorizeCache(category)

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repository.getCache()
}