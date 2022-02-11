package com.ian.junemon.foodiepedia.compose.navigation

import androidx.navigation.NavHostController


/**
 * Created by Ian Damping on 12,November,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class NavigationArgs(val route: String, val argument: Any)

data class NavigationTwoArgs(val route: String, val argument1: Any, val argument2: Any)

object Navigating{

    fun navigateWithStringArgument(
        navController: NavHostController,
        route: String,
        argument: String
    ) {
        navController.navigate("$route/$argument")
    }
    fun navigateWithIntArgument(
        navController: NavHostController,
        route: String,
        argument: Int
    ) {
        navController.navigate("$route/$argument")
    }
}
