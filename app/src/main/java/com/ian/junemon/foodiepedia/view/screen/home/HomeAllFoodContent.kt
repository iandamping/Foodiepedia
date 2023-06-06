package com.ian.junemon.foodiepedia.view.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.state.FoodUiState
import com.ian.junemon.foodiepedia.theme.MontserratFont
import com.ian.junemon.foodiepedia.view.screen.DetailScreenItem
import java.util.Locale

@Composable
fun HomeAllFoodContent(
    modifier: Modifier = Modifier,
    foodState: FoodUiState,
    bookmarkedFoodState: BookmarkedFoodUiState,
    userSearch: String,
    onContentSelectedFood: (Int) -> Unit,
) {
    when {
        foodState.errorMessage.isNotEmpty() -> {
            Column {
                Image(
                    painter = painterResource(id = R.drawable.ic_no_data),
                    contentDescription = stringResource(R.string.failed_to_load_item)
                )

                Text(
                    text = stringResource(R.string.no_item_was_found),
                    fontFamily = MontserratFont
                )
            }
        }

        foodState.data.isNotEmpty() -> {
            LazyVerticalGrid(
                modifier = modifier.height(750.dp),
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (userSearch.isEmpty()) {
                    items(foodState.data, key = { key -> key.foodId!! }) {
                        DetailScreenItem(
                            data = it,
                            bookmarkedFoodState = bookmarkedFoodState
                        ) { selectedFood ->
                            if (selectedFood.foodId != null) {
                                onContentSelectedFood.invoke(selectedFood.foodId!!)
                            }
                        }
                    }
                } else {
                    val filteredData = foodState.data.filter { filter ->
                        checkNotNull(
                            filter.foodName?.lowercase(Locale.getDefault())
                                ?.contains(userSearch)
                        )
                    }
                    if (filteredData.isNotEmpty()) {
                        items(filteredData, key = { key -> key.foodId!! }) {
                            DetailScreenItem(
                                data = it,
                                bookmarkedFoodState = bookmarkedFoodState
                            ) { selectedFood ->
                                if (selectedFood.foodId != null) {
                                    onContentSelectedFood.invoke(selectedFood.foodId!!)
                                }
                            }
                        }
                    } else {
                        items(4) {
                            Column {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_no_data),
                                    contentDescription = stringResource(R.string.failed_to_load_item)
                                )

                                Text(
                                    text = stringResource(R.string.no_item_was_found),
                                    fontFamily = MontserratFont
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}