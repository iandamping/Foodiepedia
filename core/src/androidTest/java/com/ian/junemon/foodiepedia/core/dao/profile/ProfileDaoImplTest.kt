package com.ian.junemon.foodiepedia.core.dao.profile

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.ian.junemon.foodiepedia.core.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.cache.db.ProfileDao
import com.ian.junemon.foodiepedia.core.cache.model.UserProfile
import com.ian.junemon.foodiepedia.core.cache.util.classes.ProfileDaoHelperImpl
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.ProfileDaoHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
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
@SmallTest
class ProfileDaoImplTest {

    lateinit var db: FoodDatabase
    lateinit var profileDao: ProfileDao
    lateinit var profileDaoHelper: ProfileDaoHelper

    /*
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    //this is for coroutinejob but it cannot work for me (want to consume flow), i dont know why
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    */
    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, FoodDatabase::class.java
        ).build()
        profileDao = db.profileDao()
        profileDaoHelper =
            ProfileDaoHelperImpl(
                profileDao
            )
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertProfileandGetAllData() = runBlocking {

        //GIVEN - Insert a profile data
        val profile = UserProfile(0, "test", "test", "test", "test")
        profileDaoHelper.insertAll(profile)

        //WHEN - Get the profile data from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            profileDaoHelper.loadAll().take(1).collect {
                assertThat(it, notNullValue())
                assertThat(it.localID, `is`(profile.localID))
                assertThat(it.nameUser, `is`("test"))
            }
        }
        job.cancel()
    }

    @Test
    @Throws(Exception::class)
    fun insertProfileAndDeleteData() = runBlocking {

        //GIVEN - Insert a profile data
        val profile = UserProfile(0, "test", "test", "test", "test")
        profileDaoHelper.insertAll(profile)

        // When deleting all tasks
        profileDaoHelper.deleteAllData()

        // THEN - The list is empty
        val job = launch(Dispatchers.IO) {
            profileDaoHelper.loadAll().take(1).collect {
                assertThat(null, `is`(true))
            }
        }
        job.cancel()
    }
}