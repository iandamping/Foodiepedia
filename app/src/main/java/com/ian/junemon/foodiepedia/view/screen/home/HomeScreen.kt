package com.ian.junemon.foodiepedia.view.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ian.junemon.foodiepedia.util.Constant
import com.ian.junemon.foodiepedia.viewmodel.FoodViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodViewModel = hiltViewModel(),
    selectFood: (Int) -> Unit,
) {
    val userSearchFood = viewModel.uiSearchFoodState
    val bookmarkedFood = viewModel.uiBookmarkFoodState
    val filterFood by viewModel.filterData().observeAsState(initial = "")
    val foodState = viewModel.uiFoodState
    val foodOfTheDayState = viewModel.uiFoodOfTheDayState

    HomeScreenContent(
        modifier = modifier.padding(8.dp),
        foodState = foodState,
        foodOfTheDayaState = foodOfTheDayState,
        bookmarkedFoodState = bookmarkedFood,
        userSearch = userSearchFood,
        listFilterItem = Constant.configureFilterItem(filterFood),
        onFoodSearch = { query ->
            viewModel.setSearchFood(query)
        },
        onContentSelectedFood = { id ->
            selectFood.invoke(id)
        },
        onFilterFoodSelected = { filter ->
            viewModel.setFilterData(filter)
        })


}