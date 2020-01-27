package com.ian.junemon.foodiepedia.core.data.data.repository

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.data.di.IoDispatcher
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

/**
 * Created by Ian Damping on 27,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRepositoryImpl(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val cacheDataSource: ProfileCacheDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
):ProfileRepository {
    override fun inflateLogin(): LiveData<UserProfileDataModel> {
        return liveData(ioDispatcher) {
            val disposable =
                emitSource(cacheDataSource.getCache().asLiveData())
            remoteDataSource.get().collectLatest {
                disposable.dispose()
                cacheDataSource.setCache(it)
                emitSource(cacheDataSource.getCache().asLiveData())
            }
        }
    }

    override fun get(): LiveData<UserProfileDataModel> {
        return liveData(ioDispatcher) {
            emitSource(cacheDataSource.getCache().asLiveData().distinctUntilChanged())
        }
    }

    override suspend fun initSignIn(): Intent {
        return remoteDataSource.initSignIn()
    }

    override suspend fun initLogout() {
        remoteDataSource.initLogout()
    }
}