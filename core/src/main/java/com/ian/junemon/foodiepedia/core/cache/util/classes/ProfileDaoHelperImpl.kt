package com.ian.junemon.foodiepedia.core.cache.util.classes

import com.ian.junemon.foodiepedia.core.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.cache.model.UserProfile
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.ProfileDaoHelper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileDaoHelperImpl @Inject constructor(private val userDao: ProfileDao) : ProfileDaoHelper {
    override fun loadAll(): Flow<UserProfile> {
        return userDao.loadAll()
    }

    override suspend fun insertAll(inputUser: UserProfile) {
        userDao.insertAll(inputUser)
    }

    override suspend fun deleteAllData() {
        userDao.deleteAllData()
    }
}