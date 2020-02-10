package com.ian.junemon.foodiepedia.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ian.junemon.foodiepedia.MainCoroutineRule
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.data.data.fake.FakeFoodRepository
import com.ian.junemon.foodiepedia.data.datasource.cache.FakeFoodCacheDataSourceImpl
import com.ian.junemon.foodiepedia.data.datasource.remote.FakeFoodRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.getOrAwaitValue
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.junemon.model.Results
import com.junemon.model.data.dto.mapToCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ian Damping on 07,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModelTest {

    // Subject under test
    private lateinit var fakeFoodViewModel:FoodViewModel
    private val fakeRemoteData1 = FoodRemoteDomain().apply {
        foodName = "remote"
        foodCategory = "remote"
        foodArea = "remote"
        foodImage = "remote"
        foodContributor = "remote"
        foodInstruction = "remote"
        foodIngredient = "remote"
    }

    private val listOfFakeRemote = listOf(fakeRemoteData1)
    private lateinit var cacheDataSource: FakeFoodCacheDataSourceImpl
    private lateinit var remoteDataSource: FakeFoodRemoteDataSourceImpl
    private lateinit var foodRepository: FakeFoodRepository
    private lateinit var foodUseCase: FoodUseCase


    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setupViewModel() {
        cacheDataSource =
            FakeFoodCacheDataSourceImpl()
        remoteDataSource =
            FakeFoodRemoteDataSourceImpl(
                listOfFakeRemote.toMutableList()
            )
        // Get a reference to the class under test
        foodRepository =
            FakeFoodRepository(
                remoteDataSource, cacheDataSource
            )
        foodUseCase= FoodUseCase(foodRepository)
        fakeFoodViewModel = FoodViewModel(foodUseCase)

    }

    @Test
    fun getCache() {
        val cacheData = fakeFoodViewModel.getCache().getOrAwaitValue()
        when (cacheData) {
            is Results.Success -> {
                Truth.assertThat(cacheData).isInstanceOf(Results.Success::class.java)
                Truth.assertThat(cacheData.data).hasSize(1)
                Truth.assertThat(cacheData.data[0]).isEqualTo(fakeRemoteData1.mapToCacheDomain())
            }
            is Results.Error -> {
                Truth.assertThat(cacheData).isInstanceOf(Results.Error::class.java)
            }
        }
    }

    @Test
    fun getCategorizeCache(){
        val cacheData = fakeFoodViewModel.getCategorizeCache("remote").getOrAwaitValue()
        Truth.assertThat(cacheData).hasSize(1)
        Truth.assertThat(cacheData[0]).isEqualTo(fakeRemoteData1.mapToCacheDomain())
    }
}