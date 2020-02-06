package com.ian.junemon.foodiepedia.core.data.datasource.cache

import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Created by Ian Damping on 06,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeProfileCacheDataSourceImpl(var fakeProfile: UserProfileDataModel) :
    ProfileCacheDataSource {

    var localFakeProfileData: UserProfileDataModel? = null

    override suspend fun setCache(data: UserProfileDataModel) {
        localFakeProfileData = data
    }

    override fun getCache(): Flow<UserProfileDataModel> {
        return flowOf(localFakeProfileData ?: fakeProfile)
    }
}