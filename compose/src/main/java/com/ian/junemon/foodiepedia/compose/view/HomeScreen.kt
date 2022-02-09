package com.ian.junemon.foodiepedia.compose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.ian.junemon.foodiepedia.compose.R
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.ui.theme.YellowFood
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    filterFood: String,
    setFilterFood: (String) -> Unit,
    userSearch: String,
    setUserSearch: (String) -> Unit,
    foodState: FoodUiState
) {
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    BottomSheetScreen(
        modifier = modifier.fillMaxSize(),
        state = bottomSheetScaffoldState,
        scope = coroutineScope,
        filterFood = filterFood,
        setFilterFood = { setFilterFood(it) },
        content = {

            ConstraintLayout(modifier = Modifier.fillMaxSize()) {

                val (appName, selectedFilter, filterDialogIcon, searchButton, listFood, lottieLoading) = createRefs()

                LottieLoading(modifier = Modifier.constrainAs(lottieLoading) {
                    centerVerticallyTo(parent)
                    centerHorizontallyTo(parent)
                }, isLoading = foodState.isLoading)


                Text(
                    modifier = Modifier.constrainAs(appName) {
                        centerHorizontallyTo(parent)
                    },
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.h4
                )

                Text(
                    modifier = Modifier.constrainAs(selectedFilter) {
                        top.linkTo(appName.bottom)
                        centerHorizontallyTo(parent)
                    },
                    text = filterFood, style = MaterialTheme.typography.subtitle2
                )

                Image(
                    modifier = Modifier
                        .clickable {
                            coroutineScope.launch {
                                if (!bottomSheetScaffoldState.isVisible) {
                                    bottomSheetScaffoldState.show()
                                }
                            }
                        }
                        .size(35.dp)
                        .constrainAs(filterDialogIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(appName.top)
                            bottom.linkTo(selectedFilter.bottom)
                        },
                    imageVector = Icons.Default.Sort, contentDescription = "filter"
                )

                TextField(
                    modifier = Modifier
                        .constrainAs(searchButton) {
                            top.linkTo(selectedFilter.bottom, margin = 8.dp)
                            start.linkTo(parent.start, margin = 8.dp)
                            end.linkTo(parent.end, margin = 8.dp)
                            width = Dimension.fillToConstraints
                        },
                    value = userSearch,
                    onValueChange = {
                        setUserSearch(it)
                    }, placeholder = {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                            Text(
                                text = "Search food",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    },
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(YellowFood),
                            enabled = false,
                            onClick = {}
                        ) {
                            Image(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
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
                                painter = painterResource(id = R.drawable.ic_failed),
                                contentDescription = "failed"
                            )

                            Text(text = "No item was found")
                        }
                    }
                    foodState.data.isNotEmpty() -> {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .constrainAs(listFood) {
                                    top.linkTo(searchButton.bottom)
                                }, cells = GridCells.Fixed(2)
                        ) {
                            items(items = if (userSearch.isEmpty()) {
                                foodState.data
                            } else foodState.data.filter { filter ->
                                checkNotNull(
                                    filter.foodName?.lowercase(Locale.getDefault())
                                        ?.contains(userSearch)
                                )
                            }) {
                                FoodItem(data = it)
                            }
                        }
                    }
                }
            }
        }
    )
}
