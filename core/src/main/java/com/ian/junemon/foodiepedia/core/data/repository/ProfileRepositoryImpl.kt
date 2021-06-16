package com.ian.junemon.foodiepedia.core.data.repository

import android.content.Intent
import com.ian.junemon.foodiepedia.core.data.datasource.remote.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.ian.junemon.foodiepedia.core.domain.model.DataSourceHelper
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override fun getUserProfile(): Flow<ProfileResults<AuthenticatedUserInfo>> {
        return remoteDataSource.getUserProfile().map { userResult ->
            if (userResult is DataSourceHelper.DataSourceValue) {
                ProfileResults.Success(userResult.data)
            } else {
                ProfileResults.Error(Exception("FirebaseAuth error"))
            }
        }
    }

    override suspend fun initSignIn(): Intent {
        return remoteDataSource.initSignIn()
    }

    override suspend fun initLogout(onComplete: () -> Unit) {
        remoteDataSource.initLogout(onComplete)
    }
}