package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.cache.preference.PreferenceHelper
import com.ian.junemon.foodiepedia.core.cache.preference.listener.StringPrefValueListener
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToCacheDomain
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDatabase
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDetailDatabase
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.SavedFoodDaoHelper
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterKey
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodCacheDataSourceImpl @Inject constructor(
    private val foodDao: FoodDaoHelper,
    private val savedFoodDao: SavedFoodDaoHelper,
    private val prefHelper: PreferenceHelper,
    private val stringPrefValueListener: StringPrefValueListener
) : FoodCacheDataSource {
    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return foodDao.loadFood().map { it.mapToCacheDomain() }
    }

    override fun getSavedDetailCache(): Flow<List<SavedFoodCacheDomain>> {
        return savedFoodDao.loadFood().map { it.mapToDetailDatabase() }
    }

    override fun getCategorizeCache(foodCategory: String): Flow<List<FoodCacheDomain>> {
        return foodDao.loadCategorizeFood(foodCategory).map { it.mapToCacheDomain() }
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        foodDao.insertFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        savedFoodDao.insertFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        savedFoodDao.deleteSelectedId(selectedId)
    }

    override fun registerSharedPrefStringListener() {
        prefHelper.registerListener(stringPrefValueListener)

    }

    override fun unregisterSharedPrefStringListener() {
      prefHelper.unregisterListener(stringPrefValueListener)
    }

    private fun combineString(data1: String, data2: String?): String {
        return data2 ?: data1
    }

    override fun loadSharedPreferenceFilter():Flow<String?> {
        stringPrefValueListener.setListenKey(filterKey)
        return flowOf(prefHelper.getStringInSharedPreference(filterKey))
            .combine(stringPrefValueListener.stringPreferenceValue) { a, b ->
                combineString(a, b)
            }
    }

    override fun setSharedPreferenceFilter(data: String) {
        prefHelper.saveStringInSharedPreference(filterKey, data)
    }
}