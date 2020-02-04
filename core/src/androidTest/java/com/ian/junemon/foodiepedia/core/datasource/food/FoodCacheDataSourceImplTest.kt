package com.ian.junemon.foodiepedia.core.datasource.food

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import com.ian.junemon.foodiepedia.core.cache.db.FoodDao
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.model.Food
import com.ian.junemon.foodiepedia.core.cache.util.classes.FoodDaoHelperImpl
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToCacheDomain
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import com.ian.junemon.foodiepedia.core.data.data.datasource.FoodCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.FoodCacheDataSourceImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by Ian Damping on 04,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class FoodCacheDataSourceImplTest {

    private lateinit var db: FoodDatabase
    private lateinit var foodDao: FoodDao
    private lateinit var foodDaoHelper: FoodDaoHelper
    private lateinit var cacheDataSource: FoodCacheDataSource

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java
        ).build()
        foodDao = db.foodDao()
        foodDaoHelper =
            FoodDaoHelperImpl(
                foodDao
            )
        cacheDataSource = FoodCacheDataSourceImpl(foodDaoHelper)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodandGetAllData() = runBlocking {
        //GIVEN - Insert a food
        val food = Food(0, "test", "test", "test", "test", "test", "test", "test")
        cacheDataSource.setCache(food.mapToCacheDomain())

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            cacheDataSource.getCache().take(1).collect {
                MatcherAssert.assertThat(it.size, CoreMatchers.`is`(1))
                MatcherAssert.assertThat(it[0].foodId, CoreMatchers.`is`(food.foodId))
                MatcherAssert.assertThat(it[0].foodName, CoreMatchers.`is`("test"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodandGetCategorizeData() = runBlocking {
        //GIVEN - Insert a food
        val food = Food(0, "test", "test1", "test", "test", "test", "test", "test")
        cacheDataSource.setCache(food.mapToCacheDomain())

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database with categorize test1, and contains the expected values
            cacheDataSource.getCategirizeCache("test1").take(1).collect {
                MatcherAssert.assertThat(it.size, CoreMatchers.`is`(1))
                MatcherAssert.assertThat(it[0].foodId, CoreMatchers.`is`(food.foodId))
                MatcherAssert.assertThat(it[0].foodName, CoreMatchers.`is`("test"))
            }
        }
        job.cancel()
    }
}