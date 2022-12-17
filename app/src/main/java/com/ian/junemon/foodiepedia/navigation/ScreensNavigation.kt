package com.ian.junemon.foodiepedia.navigation

import com.ian.junemon.foodiepedia.navigation.ScreensNavigation.ScreensNavigationConstant.DETAIL_SCREEN
import com.ian.junemon.foodiepedia.navigation.ScreensNavigation.ScreensNavigationConstant.HOME_SCREEN


/**
 * Created by Ian Damping on 10,November,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
sealed class ScreensNavigation {

    data class LoadHome(val name: String = HOME_SCREEN) : ScreensNavigation()
    data class LoadDetail(val name: String = DETAIL_SCREEN) : ScreensNavigation()


    private object ScreensNavigationConstant {
        const val HOME_SCREEN = "Home Screen"
        const val DETAIL_SCREEN = "Detail Screen"

    }
}

enum class ScreensNavigationArgument {
    DetailFood, RepositoryName, DetailUserName
}