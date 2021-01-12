package com.ian.junemon.foodiepedia.data.domain

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.ian.junemon.foodiepedia.MainCoroutineRule
import com.ian.junemon.foodiepedia.data.data.fake.FakeFoodRepository
import com.ian.junemon.foodiepedia.data.datasource.cache.FakeFoodCacheDataSourceImpl
import com.ian.junemon.foodiepedia.data.datasource.remote.FakeFoodRemoteDataSourceImpl
import com.ian.junemon.foodiepedia.getOrAwaitValue
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.junemon.model.WorkerResult
import com.ian.junemon.foodiepedia.core.data.model.dto.mapToCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by Ian Damping on 07,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodUseCaseImplTest {

    private val fakeRemoteData1 = FoodRemoteDomain().apply {
        foodName = "remote"
        foodCategory = "remote"
        foodArea = "remote"
        foodImage = "remote"
        foodContributor = "remote"
        foodDescription = "remote"
    }

    private val fakeSavedFoodData = SavedFoodCacheDomain(
        foodName = "remote",
        foodCategory = "remote",
        foodArea = "remote",
        foodImage = "remote",
        foodContributor = "remote",
        foodDescription = "remote",
        localFoodID = 0,
        foodId = 0
    )

    private val listOfFakeRemote = listOf(fakeRemoteData1)
    private lateinit var cacheDataSource: FakeFoodCacheDataSourceImpl
    private lateinit var remoteDataSource: FakeFoodRemoteDataSourceImpl
    private lateinit var foodRepository: FakeFoodRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createRepository() {
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
    }

    @Test
    @ExperimentalCoroutinesApi
    fun foodPrefetch() = mainCoroutineRule.runBlockingTest {
        // Trigger the repository to load data that loads from remote

        val responseStatus = foodRepository.foodPrefetch()

        launch {
            responseStatus.collect { data ->
                when (data) {
                    is WorkerResult.SuccessWork -> {
                        //data should match because we dont do anything
                        Truth.assertThat(data).isInstanceOf(WorkerResult.SuccessWork::class.java)
                    }
                    is WorkerResult.ErrorWork -> {
                        Truth.assertThat(data).isInstanceOf(WorkerResult.ErrorWork::class.java)
                    }

                    is WorkerResult.EmptyData -> {
                        Truth.assertThat(data).isInstanceOf(WorkerResult.EmptyData::class.java)
                    }
                }

            }
        }

    }

    @Test
    fun getCache() {
        val cacheData = foodRepository.getCache().getOrAwaitValue()
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
    fun getCategorizeCache() {
        val cacheData = foodRepository.getCategorizeCache("remote").getOrAwaitValue()
        Truth.assertThat(cacheData).hasSize(1)
        Truth.assertThat(cacheData[0]).isEqualTo(fakeRemoteData1.mapToCacheDomain())
    }

    @Test
    fun getSavedFood()  = runBlocking {
        foodRepository.setCacheDetailFood(fakeSavedFoodData)

        val cacheData = foodRepository.getSavedDetailCache().getOrAwaitValue()
        Truth.assertThat(cacheData).hasSize(1)
        Truth.assertThat(cacheData[0].foodArea).isEqualTo(fakeSavedFoodData.foodArea)

    }
}