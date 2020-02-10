package com.ian.junemon.foodiepedia.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ian.junemon.foodiepedia.MainCoroutineRule
import com.ian.junemon.foodiepedia.core.domain.usecase.ProfileUseCase
import com.ian.junemon.foodiepedia.data.data.fake.FakeProfileRepository
import com.ian.junemon.foodiepedia.data.datasource.cache.FakeProfileCacheDataSourceImpl
import com.ian.junemon.foodiepedia.data.datasource.remote.FakeProfileRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.getOrAwaitValue
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
    private lateinit var profileUseCase:ProfileUseCase
    private lateinit var profileViewModel:ProfileViewModel
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        cacheDataSource =
            FakeProfileCacheDataSourceImpl()
        remoteDataSource =
            FakeProfileRemoteDataSourceImpl(
                fakeProfileData
            )
        profileRepository =
            FakeProfileRepository(
                cacheDataSource,
                remoteDataSource
            )
        profileUseCase = ProfileUseCase(profileRepository)
        profileViewModel = ProfileViewModel(profileUseCase)
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