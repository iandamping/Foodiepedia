package com.ian.junemon.foodiepedia.cachehelper.savedfood

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.db.dao.SavedFoodDao
import com.ian.junemon.foodiepedia.core.cache.model.SavedFood
import com.ian.junemon.foodiepedia.core.cache.util.classes.SavedFoodDaoHelperImpl
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.SavedFoodDaoHelper
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
 * Created by Ian Damping on 25,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class SavedFoodDaoImplTest {

    lateinit var db: FoodDatabase
    lateinit var saveDao: SavedFoodDao
    lateinit var savedFoodDaoHelper: SavedFoodDaoHelper

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java
        ).build()
        saveDao = db.savedFoodDao()
        savedFoodDaoHelper =
            SavedFoodDaoHelperImpl(
                saveDao
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
        val savedFood = SavedFood(0, 0, "test", "test", "test", "test", "test", "test")
        savedFoodDaoHelper.insertFood(savedFood)

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            savedFoodDaoHelper.loadFood().take(1).collect {
                MatcherAssert.assertThat(it.size, CoreMatchers.`is`(1))
                MatcherAssert.assertThat(it[0].foodId, CoreMatchers.`is`(savedFood.foodId))
                MatcherAssert.assertThat(it[0].foodName, CoreMatchers.`is`("test"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodandGetCategorizeData() = runBlocking {
        //GIVEN - Insert a food
        val savedFood = SavedFood(
            0,
            0,
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized"
        )
        savedFoodDaoHelper.insertFood(savedFood)

        //WHEN - Get the food from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            savedFoodDaoHelper.loadCategorizeFood("categorized").take(1).collect {
                MatcherAssert.assertThat(it.size, CoreMatchers.`is`(1))
                MatcherAssert.assertThat(it[0].foodId, CoreMatchers.`is`(savedFood.foodId))
                MatcherAssert.assertThat(it[0].foodName, CoreMatchers.`is`("categorized"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodAndDeleteFood() = runBlocking {
        // Given a task inserted
        val savedFood = SavedFood(
            0,
            0,
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized"
        )
        savedFoodDaoHelper.insertFood(savedFood)
        // When deleting all tasks
        savedFoodDaoHelper.deleteAllFood()

        // THEN - The list is empty
        val job = launch(Dispatchers.IO) {
            savedFoodDaoHelper.loadFood().take(1).collect {
                MatcherAssert.assertThat(it.isEmpty(), CoreMatchers.`is`(true))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertFoodAndDeleteSelectedFoodById() = runBlocking {
        // Given a task inserted
        val savedFood = SavedFood(
            0,
            0,
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized",
            "categorized"
        )
        val savedFood2 = SavedFood(
            1,
            1,
            "categorized2",
            "categorized2",
            "categorized2",
            "categorized2",
            "categorized2",
            "categorized2"
        )
        val savedFood3 = SavedFood(
            2,
            2,
            "categorized3",
            "categorized3",
            "categorized3",
            "categorized3",
            "categorized3",
            "categorized3"
        )
        val savedFood4 = SavedFood(
            3,
            3,
            "categorized4",
            "categorized4",
            "categorized4",
            "categorized4",
            "categorized4",
            "categorized4"
        )
        val listSavedFood: MutableList<SavedFood> = mutableListOf()
        listSavedFood.add(savedFood)
        listSavedFood.add(savedFood2)
        listSavedFood.add(savedFood3)
        listSavedFood.add(savedFood4)

        savedFoodDaoHelper.insertFood(*listSavedFood.toTypedArray())
        // When deleting all tasks
        savedFoodDaoHelper.deleteSelectedId(2)

        // THEN - The list is empty
        val job = launch(Dispatchers.IO) {
            savedFoodDaoHelper.loadFood().collect {
                MatcherAssert.assertThat(it.size, CoreMatchers.`is`(3))
                MatcherAssert.assertThat(it[2].foodId, CoreMatchers.`is`(savedFood4.foodId))
                MatcherAssert.assertThat(it[2].foodName, CoreMatchers.`is`(savedFood4.foodName))
            }
        }
        job.cancel()
    }
}