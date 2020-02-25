package com.ian.junemon.foodiepedia.view

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ian.junemon.foodiepedia.ui.MainActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class HomeFragmentTest {

    @Before
    fun settingUp() {

    }
    /* private val gson by lazy { Gson() }

     private val fakeDetailFoodData = FoodCachePresentation(
         foodName = "testing detail",
         foodCategory = "testing detail",
         foodArea = "testing detail",
         foodImage = "testing detail",
         foodContributor = "testing detail",
         foodDescription = "testing detail",
         foodId = 0
     )*/

    @Test
    fun displayedFoodUiTest() {
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(2000)
        /*val bundle = DetailFragmentArgs(gson.toJson(fakeDetailFoodData)).toBundle()
        launchFragmentInContainer<DetailFragment>(bundle, R.style.AppTheme)
        Thread.sleep(2000)*/
    }
}