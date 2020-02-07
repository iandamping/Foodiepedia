package com.ian.junemon.foodiepedia.core.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ian.junemon.foodiepedia.core.MainCoroutineRule
import com.ian.junemon.foodiepedia.core.data.data.fake.FakeProfileRepository
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FakeProfileCacheDataSourceImpl
import com.ian.junemon.foodiepedia.core.data.datasource.remote.FakeProfileRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.core.getOrAwaitValue
import com.ian.junemon.foodiepedia.core.testonly.FakeProfileViewModel
import com.junemon.model.domain.UserProfileDataModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ian Damping on 07,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileViewModelTest {

    private val fakeProfileData = UserProfileDataModel(0, "profle", "profle", "profle", "profle")
    private lateinit var cacheDataSource: FakeProfileCacheDataSourceImpl
    private lateinit var remoteDataSource: FakeProfileRemoteDataSourceImpl
    private lateinit var profileRepository: FakeProfileRepository
    private lateinit var profileViewModel:FakeProfileViewModel
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        cacheDataSource = FakeProfileCacheDataSourceImpl()
        remoteDataSource = FakeProfileRemoteDataSourceImpl(fakeProfileData)
        profileRepository = FakeProfileRepository(cacheDataSource, remoteDataSource)
        profileViewModel = FakeProfileViewModel(profileRepository)
    }

    @Test
    fun inflateLogin(){
        val result = profileViewModel.inflateLogin().getOrAwaitValue()
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isEqualTo(fakeProfileData)
    }

    @Test
    fun get(){
        val result = profileViewModel.getUser().getOrAwaitValue()
        Truth.assertThat(result).isNotNull()
        Truth.assertThat(result).isEqualTo(fakeProfileData)
    }
}