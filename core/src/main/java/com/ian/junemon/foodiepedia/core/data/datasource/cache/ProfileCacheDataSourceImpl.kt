package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDatabase
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDomain
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.ProfileDaoHelper
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
class ProfileCacheDataSourceImpl @Inject constructor(private val profileDao: ProfileDaoHelper) :
    ProfileCacheDataSource {

    override suspend fun setCache(data: UserProfileDataModel) {
        profileDao.insertAll(data.mapToDatabase())
    }

    override fun getCache(): Flow<UserProfileDataModel> {
        return profileDao.loadAll().mapToDomain().distinctUntilChanged()
    }
}