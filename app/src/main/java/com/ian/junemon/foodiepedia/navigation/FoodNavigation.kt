package com.ian.junemon.foodiepedia.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ian.junemon.foodiepedia.intentShareImageAndText
import com.ian.junemon.foodiepedia.view.screen.DetailScreen
import com.ian.junemon.foodiepedia.view.screen.HomeScreen

@Composable
fun FoodNavigationHost(
    navController: NavHostController
) {
    val context = LocalContext.current
    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.LoadHome().name,
        builder = {
            composable(ScreensNavigation.LoadHome().name) {
                HomeScreen(
                    selectFood = { selectedFoodId ->
                        navController.navigate("${ScreensNavigation.LoadDetail().name}/$selectedFoodId")
                    }
                )
            }

            composable(
                "${ScreensNavigation.LoadDetail().name}/{${ScreensNavigationArgument.DetailFood.name}}",
                arguments = listOf(navArgument(ScreensNavigationArgument.DetailFood.name) {
                    type = NavType.IntType
                })
            ) {
                DetailScreen(
                    shareData = { data ->
                        intentShareImageAndText(
                            tittle = data.foodName,
                            imageUrl = data.foodImage,
                            message = data.foodCategory,
                            context = context
                        )
                    },
                    navigateUp = {
                        navController.navigateUp()
                    }
                )
            }

        }
    )

}
