package com.ian.junemon.foodiepedia.core.cachehelper.food

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ian.junemon.foodiepedia.core.cache.db.FoodDao
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.model.Food
import com.ian.junemon.foodiepedia.core.cache.util.classes.FoodDaoHelperImpl
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.FoodDaoHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FoodDaoImplTest {

    lateinit var db: FoodDatabase
    lateinit var foodDao: FoodDao
    lateinit var foodDaoHelper: FoodDaoHelper

    /*
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    //this is for coroutinejob but it cannot work for me (want to consume flow), i dont know why
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()*/

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java
        ).build()
        foodDao = db.foodDao()
        foodDaoHelper =
            FoodDaoHelperImpl(
                foodDao
            )
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
        foodDaoHelper.insertFood(food)

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            foodDaoHelper.loadFood().take(1).collect {
                assertThat(it.size, `is`(1))
                assertThat(it[0].foodId, `is`(food.foodId))
                assertThat(it[0].foodName, `is`("test"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodandGetCategorizeData() = runBlocking {
        //GIVEN - Insert a food
        val food = Food(0, "test", "test1", "test", "test", "test", "test", "test")
        foodDaoHelper.insertFood(food)

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database with categorize test1, and contains the expected values
            foodDaoHelper.loadCategorizeFood("test1").take(1).collect {
                assertThat(it.size, `is`(1))
                assertThat(it[0].foodId, `is`(food.foodId))
                assertThat(it[0].foodName, `is`("test"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodAndDeleteFood() = runBlocking {
        // Given a task inserted
        val food = Food(0, "test", "test", "test", "test", "test", "test", "test")
        foodDaoHelper.insertFood(food)

        // When deleting all tasks
        foodDaoHelper.deleteAllFood()

        // THEN - The list is empty
        val job = launch(Dispatchers.IO) {
            foodDaoHelper.loadFood().take(1).collect {
                assertThat(it.isEmpty(), `is`(true))
            }
        }
        job.cancel()
    }
}