package com.ian.junemon.foodiepedia.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.asLiveData
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ian.junemon.foodiepedia.compose.FoodViewModel
import com.ian.junemon.foodiepedia.compose.intentShareImageAndText
import com.ian.junemon.foodiepedia.compose.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.compose.state.DetailFoodUiState
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.view.FoodDetailScreen
import com.ian.junemon.foodiepedia.compose.view.HomeScreen
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabasePresentation

@Composable
fun FoodNavigationHost(
    viewModel: FoodViewModel,
    navController: NavHostController
) {
    val context = LocalContext.current
    val filterFood by viewModel.filterData().observeAsState(initial = "")
    val listOfFood by viewModel.food.asLiveData().observeAsState(initial = FoodUiState.initial())
    val detailFood by viewModel.detailFood.asLiveData().observeAsState(initial = DetailFoodUiState.initial())
    val bookmarkedFood by viewModel.bookmarkedFood.asLiveData().observeAsState(initial = BookmarkedFoodUiState.initial())

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
                    viewModel.setSelectedFoodId(selectedFood.foodId ?: 0)
                    navController.navigate(ScreensNavigation.LoadDetail().name)
                }
            }

            composable(ScreensNavigation.LoadDetail().name) {

                FoodDetailScreen(
                    navController = navController,
                    data = detailFood,
                    bookmarkedFood = bookmarkedFood,
                    shareData = { data ->
                        intentShareImageAndText(
                            tittle = data.foodName,
                            imageUrl = data.foodImage,
                            message = data.foodCategory,
                            context = context
                        )
                    },
                    bookmarkData = { data ->
                        viewModel.bookmarkFood(data.mapToDetailDatabasePresentation())
                    },
                    unbookmarkData = viewModel::unbookmarkFood
                )
            }

        }
    )

}
