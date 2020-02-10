package com.ian.junemon.foodiepedia.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.feature.view.HomeFragment
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Ian Damping on 10,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@RunWith(AndroidJUnit4::class)
@MediumTest
class HomeFragmentTest {

    @Test
    fun displayedFoodUiTest(){
        launchFragmentInContainer<HomeFragment>(fragmentArgs = null, themeResId = R.style.AppTheme)
        Thread.sleep(2000)
    }
}