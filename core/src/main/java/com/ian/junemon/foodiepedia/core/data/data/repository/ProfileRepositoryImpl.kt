package com.ian.junemon.foodiepedia.core.data.data.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.junemon.model.DataHelper
import com.junemon.model.ProfileResults
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
    private val cacheDataSource: ProfileCacheDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {
    override fun getUserProfile():LiveData<ProfileResults<UserProfileDataModel>> {
        return liveData {
            val disposables = emitSource(flowOf(ProfileResults.Loading).asLiveData())
            remoteDataSource.getUserProfile().collect {response ->
                when(response){
                    is DataHelper.RemoteSourceError -> {
                        emitSource(flowOf(ProfileResults.Error(response.exception)).asLiveData())
                    }
                    is DataHelper.RemoteSourceValue -> {
                        disposables.dispose()
                        cacheDataSource.setCache(response.data)
                        emitSource(cacheDataSource.getCache().mapLatest {
                            checkNotNull(it)
                            ProfileResults.Success(it)
                        }.catch {throwable ->
                            emitSource(flowOf(ProfileResults.Error(Exception(throwable))).asLiveData())
                        }.asLiveData() )
                    }
                }
            }
        }
    }

    override fun getCacheUserProfile(): LiveData<UserProfileDataModel> {
        return liveData {
            cacheDataSource.getCache().collect {
                emit(it)
            }
        }
    }

    override suspend fun initSignIn(): Intent {
        return remoteDataSource.initSignIn()
    }

    override suspend fun initLogout() {
        remoteDataSource.initLogout()
        cacheDataSource.deleteCache()
    }
}