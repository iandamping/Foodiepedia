package com.ian.junemon.foodiepedia.view.screen.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.state.FilterItem
import com.ian.junemon.foodiepedia.state.FoodOfTheDayUiState
import com.ian.junemon.foodiepedia.state.FoodUiState
import com.ian.junemon.foodiepedia.theme.MontserratFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    foodState: FoodUiState,
    foodOfTheDayaState: FoodOfTheDayUiState,
    bookmarkedFoodState: BookmarkedFoodUiState,
    userSearch: String,
    listFilterItem: List<FilterItem>,
    onContentSelectedFood: (Int) -> Unit,
    onFoodSearch: (String) -> Unit,
    onFilterFoodSelected: (String) -> Unit
) {
    val listState = rememberLazyListState()
    val foodOfTheDayState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()) {
        val showButton by remember {
            derivedStateOf {
                listState.firstVisibleItemIndex > 0
            }
        }
        LazyColumn(state = listState) {
            item {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h4,
                    fontFamily = MontserratFont
                )

            }

            item {
                HomeSearchFoodContent(userSearch = userSearch, onFoodSearch = onFoodSearch)
            }

            item {
                HomeFilterChipSelectionContent(
                    listFilterItem = listFilterItem,
                    onFilterFoodSelected = onFilterFoodSelected
                )
            }

            item {
                HomeFoodOfTheDayContent(
                    lazyListState = foodOfTheDayState,
                    foodOfTheDaysUiState = foodOfTheDayaState,
                    userSearch = userSearch,
                    onContentSelectedFood = onContentSelectedFood
                )
            }

            item {
                Text(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                    text = "All food",
                    style = MaterialTheme.typography.h5,
                    fontFamily = MontserratFont
                )

                HomeAllFoodContent(
                    foodState = foodState,
                    bookmarkedFoodState = bookmarkedFoodState,
                    userSearch = userSearch,
                    onContentSelectedFood = onContentSelectedFood
                )
            }
        }


        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = showButton
        ) {
            Button(shape = RoundedCornerShape(20.dp),
                onClick = {
                    scope.launch {
                        // Animate scroll to the first item
                        listState.animateScrollToItem(index = 0)
                    }
                }
            ) {
                Icon(
                    Icons.Default.ArrowUpward,
                    contentDescription = stringResource(R.string.back_to_top)
                )
                Text(text = stringResource(R.string.back_to_top), fontFamily = MontserratFont)
            }

        }
    }


}
