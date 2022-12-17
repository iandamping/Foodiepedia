package com.ian.junemon.foodiepedia.view.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.HighlightOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.FoodUiState
import com.ian.junemon.foodiepedia.view.LottieLoading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    scope: CoroutineScope = rememberCoroutineScope(),
    state: ModalBottomSheetState,
    foodState: FoodUiState,
    userSearch: String,
    onContentSelectedFood: (Int) -> Unit,
    onFoodSearch: (String) -> Unit
) {
    val listState = rememberLazyGridState()

    ConstraintLayout(modifier = modifier.fillMaxSize()) {

        val (appName, filterDialogIcon, searchButton, listFood, lottieLoading, scrollToTop) = createRefs()

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

        Image(
            modifier = Modifier
                .clickable {
                    scope.launch {
                        if (!state.isVisible) {
                            state.show()
                        }
                    }
                }
                .size(35.dp)
                .constrainAs(filterDialogIcon) {
                    end.linkTo(parent.end, margin = 8.dp)
                    top.linkTo(appName.top, margin = 8.dp)
                },
            imageVector = Icons.Default.Sort, contentDescription = stringResource(R.string.filter_food)
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



        when {
            foodState.errorMessage.isNotEmpty() -> {
                Column(modifier = Modifier.constrainAs(listFood) {
                    top.linkTo(searchButton.bottom)
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
                            top.linkTo(searchButton.bottom, margin = 8.dp)
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

                        DetailScreenItem(data = it) { selectedFood ->
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
