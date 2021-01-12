package com.ian.junemon.foodiepedia.data.data.fake

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.liveData
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileRemoteDataSource
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.ian.junemon.foodiepedia.core.domain.model.domain.UserProfileDataModel
import kotlinx.coroutines.flow.collect

/**
 * Created by Ian Damping on 07,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FakeProfileRepository(
    private val cacheDataSource: ProfileCacheDataSource,
    private val remoteDataSource: ProfileRemoteDataSource
):ProfileRepository {
    override fun getUserProfile(): LiveData<UserProfileDataModel> {
        return liveData {
            remoteDataSource.getUserProfile().collect {
                // because i dont use real db, i try to save it the data first by calling this setCache func
                cacheDataSource.setCache(it)
                emitSource(cacheDataSource.getCache().asLiveData())
            }
        }
    }

    override fun get(): LiveData<UserProfileDataModel> {
        return liveData {
            val responseStatus = remoteDataSource.getUserProfile()
            responseStatus.collect {
                // because i dont use real db, i try to save it the data first by calling this setCache func
                cacheDataSource.setCache(it)
            }
            emitSource(cacheDataSource.getCache().asLiveData().distinctUntilChanged())
        }
    }

    override suspend fun initSignIn(): Intent {
        return Intent()
    }

    override suspend fun initLogout() {
    }
}