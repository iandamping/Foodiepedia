package com.ian.junemon.foodiepedia.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.state.FilterItem
import com.ian.junemon.foodiepedia.state.FoodUiState
import com.ian.junemon.foodiepedia.view.LottieLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    foodState: FoodUiState,
    bookmarkedFoodState: BookmarkedFoodUiState,
    userSearch: String,
    listFilterItem: List<FilterItem>,
    onContentSelectedFood: (Int) -> Unit,
    onFoodSearch: (String) -> Unit,
    onFilterFoodSelected: (String) -> Unit
) {
    val listState = rememberLazyGridState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {

        val (appName, searchButton, chipGroups, listFood, lottieLoading, scrollToTop) = createRefs()

        LottieLoading(modifier = Modifier.constrainAs(lottieLoading) {
            centerVerticallyTo(parent)
            centerHorizontallyTo(parent)
        }, isLoading = foodState.isLoading)


        Text(
            modifier = Modifier.constrainAs(appName) {
                start.linkTo(parent.start, margin = 8.dp)
                top.linkTo(parent.top, margin = 8.dp)
            },
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h4
        )

        OutlinedTextField(
            modifier = Modifier
                .constrainAs(searchButton) {
                    top.linkTo(appName.bottom, margin = 8.dp)
                    start.linkTo(parent.start, margin = 8.dp)
                    end.linkTo(parent.end, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            value = userSearch,
            onValueChange = { query ->
                onFoodSearch.invoke(query)
            }, placeholder = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = stringResource(R.string.search_food),
                        style = MaterialTheme.typography.body2
                    )
                }
            },
            trailingIcon = {
                Icon(
                    modifier = Modifier.clickable {
                        onFoodSearch.invoke("")
                    },
                    imageVector = Icons.Default.HighlightOff,
                    contentDescription = stringResource(R.string.clear_search)
                )
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true
        )

        LazyRow(modifier = Modifier.constrainAs(chipGroups) {
            top.linkTo(searchButton.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 8.dp)
            end.linkTo(parent.end, margin = 8.dp)
            width = Dimension.fillToConstraints
        }, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listFilterItem) { singleItem ->
                Chip(
                    onClick = {
                        onFilterFoodSelected.invoke(singleItem.filterText)
                    },
                    leadingIcon = {
                        if (singleItem.isFilterSelected) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_check_circle_green_24dp),
                                contentDescription = stringResource(R.string.select_filter)
                            )
                        }
                    },
                    border = BorderStroke(1.dp, Color.Black),
                    colors = ChipDefaults.chipColors(backgroundColor = Color.White)
                ) {


                    Text(text = singleItem.filterText)
                }
            }
        }



        when {
            foodState.errorMessage.isNotEmpty() -> {
                Column(modifier = Modifier.constrainAs(listFood) {
                    top.linkTo(chipGroups.bottom)
                    bottom.linkTo(parent.bottom)
                    centerHorizontallyTo(parent)
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_no_data),
                        contentDescription = stringResource(R.string.failed_to_load_item)
                    )

                    Text(text = stringResource(R.string.no_item_was_found))
                }
            }
            foodState.data.isNotEmpty() -> {
                LazyVerticalGrid(
                    modifier = Modifier
                        .constrainAs(listFood) {
                            top.linkTo(chipGroups.bottom, margin = 8.dp)
                            bottom.linkTo(parent.bottom, margin = 8.dp)
                            height = Dimension.fillToConstraints
                        },
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    state = listState
                ) {
                    items(items = if (userSearch.isEmpty()) {
                        foodState.data
                    } else foodState.data.filter { filter ->
                        checkNotNull(
                            filter.foodName?.lowercase(Locale.getDefault())
                                ?.contains(userSearch)
                        )
                    }, key = { key -> key.foodId!! }) {

                        DetailScreenItem(data = it, bookmarkedFoodState = bookmarkedFoodState) { selectedFood ->
                            if (selectedFood.foodId != null) {
                                onContentSelectedFood.invoke(selectedFood.foodId!!)
                            }
                        }
                    }
                }

                val showButton by remember {
                    derivedStateOf {
                        listState.firstVisibleItemIndex > 0
                    }
                }

                AnimatedVisibility(
                    visible = showButton,
                    modifier = Modifier.constrainAs(scrollToTop) {
                        bottom.linkTo(parent.bottom, 12.dp)
                        centerHorizontallyTo(parent)
                    }) {
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
                        Text(text = stringResource(R.string.back_to_top))
                    }

                }
            }
        }
    }
}
