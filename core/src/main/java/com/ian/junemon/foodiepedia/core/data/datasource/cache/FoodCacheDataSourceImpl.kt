package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.data.datasource.cache.datastore.DataStoreHelper
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.FoodDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.SavedFoodDao
import com.ian.junemon.foodiepedia.core.util.DataConstant
import com.ian.junemon.foodiepedia.core.util.DataConstant.ERROR_EMPTY_DATA
import com.ian.junemon.foodiepedia.core.util.mapToCacheDomain
import com.ian.junemon.foodiepedia.core.util.mapToDatabase
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabase
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodCacheDataSourceImpl @Inject constructor(
    private val foodDao: FoodDao,
    private val savedFoodDao: SavedFoodDao,
    private val dataHelper: DataStoreHelper
) : FoodCacheDataSource {
    override fun getCache(): Flow<List<FoodCacheDomain>> {
        return foodDao.loadFood().map { it.mapToCacheDomain() }
    }

    override fun getCacheById(id: Int): Flow<FoodCacheDomain> {
        return foodDao.loadFoodById(id).map { it.mapToCacheDomain() }
    }

    override fun getSavedDetailCache(): Flow<DataSourceHelper<List<SavedFoodCacheDomain>>> {
        return savedFoodDao.loadFood().map {
            if (it.isNullOrEmpty()) {
                DataSourceHelper.DataSourceError(Exception(ERROR_EMPTY_DATA))
            } else {
                DataSourceHelper.DataSourceValue(it.mapToDetailDatabase())
            }
        }
    }

    override fun getCategorizeCache(foodCategory: String): Flow<DataSourceHelper<List<FoodCacheDomain>>> {
        return foodDao.loadFoodByCategory(foodCategory).map {
            if (it.isNullOrEmpty()) {
                DataSourceHelper.DataSourceError(Exception(ERROR_EMPTY_DATA))
            } else {
                DataSourceHelper.DataSourceValue(it.mapToCacheDomain())
            }
        }
    }

    override suspend fun setCache(vararg data: FoodCacheDomain) {
        foodDao.deleteAllFood()
        foodDao.saveFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun setCacheDetailFood(vararg data: SavedFoodCacheDomain) {
        savedFoodDao.insertFood(*data.map { it.mapToDatabase() }.toTypedArray())
    }

    override suspend fun deleteSelectedId(selectedId: Int) {
        savedFoodDao.deleteSelectedId(selectedId)
    }

    override fun loadSharedPreferenceFilter(): Flow<String> {
        return dataHelper.getStringInDataStore(DataConstant.FILTER_KEY)
    }

    override suspend fun setSharedPreferenceFilter(data: String) {
        dataHelper.saveStringInDataStore(DataConstant.FILTER_KEY, data)
    }

    override suspend fun deleteFood() {
        savedFoodDao.deleteAllFood()
    }
}