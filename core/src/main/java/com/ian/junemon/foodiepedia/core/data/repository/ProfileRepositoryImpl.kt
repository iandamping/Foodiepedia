package com.ian.junemon.foodiepedia.core.data.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.core.data.datasource.remote.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.data.datasource.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.DataSourceHelper
import com.junemon.model.ProfileResults
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {


    override fun getUserProfile():LiveData<ProfileResults<AuthenticatedUserInfo>> {
        return flow {
            remoteDataSource.getUserProfile().collect { userResult ->
                if (userResult is DataSourceHelper.DataSourceValue){
                    emit(ProfileResults.Success(userResult.data))
                } else{
                   emit(ProfileResults.Error(Exception("FirebaseAuth error")))
                }
            }
        }.asLiveData()
    }


    override suspend fun initSignIn(): Intent {
        return remoteDataSource.initSignIn()
    }

    override suspend fun initLogout(onComplete: () -> Unit) {
        remoteDataSource.initLogout(onComplete)
    }
}