package com.ian.junemon.foodiepedia.core.data.data

import com.google.common.truth.Truth
import com.ian.junemon.foodiepedia.core.data.data.repository.ProfileRepositoryImpl
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FakeProfileCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FakeProfileRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.domain.repository.ProfileRepository
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

/**
 * Created by Ian Damping on 06,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileRepositoryImplTest {

    private val fakeProfileData = UserProfileDataModel(0, "profle", "profle", "profle", "profle")

    private lateinit var cacheDataSource: FakeProfileCacheDataSourceImpl
    private lateinit var remoteDataSource: FakeProfileRemoteDataSourceImpl
    private lateinit var profileRepository: ProfileRepository

    @Before
    fun setup(){
        cacheDataSource = FakeProfileCacheDataSourceImpl()
        remoteDataSource = FakeProfileRemoteDataSourceImpl(fakeProfileData)
        profileRepository = ProfileRepositoryImpl(Dispatchers.Unconfined,cacheDataSource, remoteDataSource)
    }

    @Test
    fun getUser() = runBlocking{
        val remoteData = remoteDataSource.get()
        val job = launch {
            remoteData.collect { data ->
                Truth.assertThat(data.nameUser).isEqualTo(fakeProfileData.nameUser)
                Truth.assertThat(data.emailUser).isEqualTo(fakeProfileData.emailUser)
                Truth.assertThat(data.photoUser).isEqualTo(fakeProfileData.photoUser)
                cacheDataSource.setCache(data)
            }
        }
        val job2 = launch {
            cacheDataSource.getCache().collect { data ->
                Truth.assertThat(data.nameUser).isEqualTo(fakeProfileData.nameUser)
            }
        }
        job.cancel()
        job2.cancel()

    }
}