package com.ian.junemon.foodiepedia.core.data.data.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.remote.firebaseuser.AuthenticatedUserInfo
import com.junemon.model.DataHelper
import com.junemon.model.ProfileResults
import com.junemon.model.Results
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
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
                if (userResult is DataHelper.RemoteSourceValue){
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