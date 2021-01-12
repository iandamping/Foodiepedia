package com.ian.junemon.foodiepedia.data.cache

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.FoodDatabase
import com.ian.junemon.foodiepedia.core.data.datasource.cache.db.dao.ProfileDao
import com.ian.junemon.foodiepedia.core.data.datasource.cache.model.UserProfile
import com.ian.junemon.foodiepedia.core.cache.util.classes.ProfileDaoHelperImpl
import com.ian.junemon.foodiepedia.core.cache.util.dto.mapToDomain
import com.ian.junemon.foodiepedia.core.cache.util.interfaces.ProfileDaoHelper
import com.ian.junemon.foodiepedia.core.data.data.datasource.ProfileCacheDataSource
import com.ian.junemon.foodiepedia.core.data.datasource.cache.ProfileCacheDataSourceImpl
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
class ProfileCacheDataSourceImplTest {
    lateinit var db: FoodDatabase
    lateinit var profileDao: ProfileDao
    lateinit var profileDaoHelper: ProfileDaoHelper

    private lateinit var cacheDataSource: ProfileCacheDataSource

    @ExperimentalCoroutinesApi
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
        cacheDataSource = ProfileCacheDataSourceImpl(profileDaoHelper)
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
        cacheDataSource.setCache(profile.mapToDomain())

        //WHEN - Get the profile data from the database
        val job = launch(Dispatchers.IO) {
            // THEN - There is only 1 task in the database, and contains the expected values
            cacheDataSource.getCache().take(1).collect {
                MatcherAssert.assertThat(it, CoreMatchers.notNullValue())
                MatcherAssert.assertThat(it.localID, CoreMatchers.`is`(profile.localID))
                MatcherAssert.assertThat(it.nameUser, CoreMatchers.`is`("test"))
            }
        }
        job.cancel()
    }
}