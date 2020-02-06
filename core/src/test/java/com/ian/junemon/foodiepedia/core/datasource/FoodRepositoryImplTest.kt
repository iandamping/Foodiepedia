package com.ian.junemon.foodiepedia.core.datasource

import com.google.common.truth.Truth.assertThat
import com.ian.junemon.foodiepedia.core.data.data.repository.FoodRepositoryImpl
import com.ian.junemon.foodiepedia.core.domain.repository.FoodRepository
import com.ian.junemon.foodiepedia.core.getOrAwaitValue
import com.junemon.model.DataHelper
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

/**
 * Created by Ian Damping on 05,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodRepositoryImplTest {

    private val fakeCacheData1 =
        FoodCacheDomain(0, "cache", "cache", "cache", "cache", "cache", "cache", "cache")
    private val fakeCacheData2 =
        FoodCacheDomain(1, "cache2", "cache2", "cache2", "cache2", "cache2", "cache2", "cache2")
    private val fakeCacheData3 =
        FoodCacheDomain(2, "cache3", "cache3", "cache3", "cache3", "cache3", "cache3", "cache3")
    private val fakeRemoteData1 = FoodRemoteDomain().apply {
        foodName = "remote"
        foodCategory = "remote"
        foodArea = "remote"
        foodImage = "remote"
        foodContributor = "remote"
        foodInstruction = "remote"
        foodIngredient = "remote"
    }
    private val fakeRemoteData2 = FoodRemoteDomain().apply {
        foodName = "remote2"
        foodCategory = "remote2"
        foodArea = "remote2"
        foodImage = "remote2"
        foodContributor = "remote2"
        foodInstruction = "remote2"
        foodIngredient = "remote2"
    }
    private val fakeRemoteData3 = FoodRemoteDomain().apply {
        foodName = "remote3"
        foodCategory = "remote3"
        foodArea = "remote3"
        foodImage = "remote3"
        foodContributor = "remote3"
        foodInstruction = "remote3"
        foodIngredient = "remote3"
    }

    private val listOfFakeCache = listOf(fakeCacheData1, fakeCacheData2, fakeCacheData3)
    private val listOfFakeRemote = listOf(fakeRemoteData1, fakeRemoteData2, fakeRemoteData3)
    private lateinit var cacheDataSource: FakeFoodCacheDataSource
    private lateinit var remoteDataSource: FakeFoodRemoteDataSource
    private lateinit var foodRepository: FoodRepository

    @Before
    fun createRepository() {
        cacheDataSource = FakeFoodCacheDataSource(listOfFakeCache.toMutableList())
        // remoteDataSource = FakeFoodRemoteDataSource(listOfFakeRemote.toMutableList(), fakeUri)
        remoteDataSource = FakeFoodRemoteDataSource(listOfFakeRemote.toMutableList())
        // Get a reference to the class under test
        foodRepository = FoodRepositoryImpl(
            Dispatchers.Unconfined,
            remoteDataSource, cacheDataSource
        )
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repositoryFoodPrefetch() = runBlocking {
        // Trigger the repository to load data that loads from remote
        val responseStatus = remoteDataSource.getFirebaseData()

        val job = launch(Dispatchers.IO) {
            responseStatus.take(1).collect { data ->
                when (data) {
                    is DataHelper.RemoteSourceValue -> {
                        //data should match because we dont do anything
                        assertThat(data.data).hasSize(3)
                        assertThat(data.data[0]).isEqualTo(fakeRemoteData1)
                        assertThat(data.data[1]).isEqualTo(fakeRemoteData2)
                        assertThat(data.data[2]).isEqualTo(fakeRemoteData3)
                    }
                }

            }
        }
        job.cancel()
    }

    @Test
    fun repositoryFoodPrefetch_failScenario() = runBlocking {
        // Trigger the repository to load null data from remote
        remoteDataSource = FakeFoodRemoteDataSource(null)
        val responseStatus = remoteDataSource.getFirebaseData()
        val job = launch(Dispatchers.IO) {
            responseStatus.collect { data ->
                assertThat(data).isInstanceOf(DataHelper.RemoteSourceError::class.java)
                /*when (data) {
                    is DataHelper.RemoteSourceError -> {
                        //data should match because we throwing nullPointerException
                        assertThat<NullPointerException>(data.exception as NullPointerException, CoreMatchers.notNullValue())
                    }
                }*/
            }
        }
        job.cancel()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repositoryFoodGetCache() = runBlocking {
        // Trigger the repository to load data that loads from remote
        val cacheData = cacheDataSource.getCache()

        val job = launch(Dispatchers.IO) {
            cacheData.take(1).collect { data ->
                assertThat(data).hasSize(3)
                assertThat(data[0]).isEqualTo(fakeCacheData1)
                assertThat(data[1]).isEqualTo(fakeCacheData2)
                assertThat(data[2]).isEqualTo(fakeCacheData3)

            }
        }
        job.cancel()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repositoryFoodGetCategorizeCache() = runBlocking {
        // Trigger the repository to load data that loads from remote
        val cacheData = cacheDataSource.getCategirizeCache("cache2")

        val job = launch(Dispatchers.IO) {
            cacheData.take(1).collect { data ->
                assertThat(data[0]).isEqualTo(fakeCacheData2)
            }
        }
        job.cancel()
    }

   /* @Test
    @ExperimentalCoroutinesApi
    fun repositoryFoodGetCache() = runBlockingTest {
        // Trigger the repository to load data that loads from remote
        val cacheData = foodRepository.getCache().getOrAwaitValue()

        when(cacheData){
            is Results.Success ->{
                assertThat(cacheData.data).hasSize(3)
                assertThat(cacheData.data[0]).isEqualTo(fakeCacheData1)
                assertThat(cacheData.data[1]).isEqualTo(fakeCacheData2)
                assertThat(cacheData.data[2]).isEqualTo(fakeCacheData3)
            }
        }
    }

    @Test
    @ExperimentalCoroutinesApi
    fun repositoryFoodGetCategorizeCache() = runBlockingTest {
        // Trigger the repository to load data that loads from remote
        val cacheData = foodRepository.getCategorizeCache("cache2").getOrAwaitValue()
        assertThat(cacheData[0]).isEqualTo(fakeCacheData2)

    }*/
}