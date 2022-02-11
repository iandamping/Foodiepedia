package com.ian.junemon.foodiepedia.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ian.junemon.foodiepedia.compose.FoodViewModel
import com.ian.junemon.foodiepedia.compose.navigation.Navigating.navigateWithIntArgument
import com.ian.junemon.foodiepedia.compose.state.DetailFoodUiState
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.view.FoodDetailScreen
import com.ian.junemon.foodiepedia.compose.view.HomeScreen

@Composable
fun FoodNavigationHost(
    viewModel: FoodViewModel,
    navController: NavHostController
) {
    val filterFood by viewModel.filterData().observeAsState(initial = "")
    val listOfFood by viewModel.food.asLiveData().observeAsState(initial = FoodUiState.initial())
    val detailFood by viewModel.detailFood.asLiveData().observeAsState(initial = DetailFoodUiState.initial())
    val userSearchFood by viewModel.searchFood.asLiveData().observeAsState(initial = "")

    NavHost(
        navController = navController,
        startDestination = ScreensNavigation.LoadHome().name,
        builder = {
            composable(ScreensNavigation.LoadHome().name) {
                HomeScreen(
                    filterFood = filterFood,
                    userSearch = userSearchFood,
                    foodState = listOfFood,
                    setFilterFood = viewModel::setFilterData,
                    setUserSearch = viewModel::setSearchFood
                ) { selectedFood ->
                    viewModel.getFoodById(selectedFood.foodId ?: 0)
                    navController.navigate(ScreensNavigation.LoadDetail().name)
                }
            }

            composable(ScreensNavigation.LoadDetail().name) {
                FoodDetailScreen(
                    navController = navController,
                    data = detailFood
                )
            }

        }
    )

}
