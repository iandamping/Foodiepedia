package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.util.mapToDatabase
import com.ian.junemon.foodiepedia.core.util.mapToDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.UserProfileDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
class ProfileCacheDataSourceImpl @Inject constructor(private val profileDao: ProfileDao) :
    ProfileCacheDataSource {

    override suspend fun setCache(data: UserProfileDataModel) {
        profileDao.deleteAllData().let {
            profileDao.insertAllUser(data.mapToDatabase())
        }
    }

    override fun getCache(): Flow<UserProfileDataModel> {
        return profileDao.loadAll().mapToDomain()
    }

    override suspend fun deleteCache() {
        profileDao.deleteAllData()
    }
}