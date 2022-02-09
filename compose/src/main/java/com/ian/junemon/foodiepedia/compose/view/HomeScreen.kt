package com.ian.junemon.foodiepedia.compose.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.compose.Constant.configureFilterItem
import com.ian.junemon.foodiepedia.compose.R
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.compose.ui.theme.YellowFood
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
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
    var isFilterClicked by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    ModalBottomSheetLayout(
        modifier = modifier.fillMaxSize(),
        sheetState = bottomSheetScaffoldState,
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                IconButton(modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.End), onClick = {
                    coroutineScope.launch {
                        if (bottomSheetScaffoldState.isVisible) {
                            bottomSheetScaffoldState.hide()
                        }
                    }
                }, content = {
                    Image(imageVector = Icons.Default.Close, contentDescription = "Close Item")
                })

                LazyColumn {
                    items(configureFilterItem(filterFood)) { singleItem ->
                        FilterFoodDialog(filterItem = singleItem, selectedFilterItem = {
                            setFilterFood(it.filterText)
                        })
                    }
                }
            }

        }) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {

            val (appName, selectedFilter, filterDialogIcon, searchButton, listFood) = createRefs()

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
                        onClick = {
                            isFilterClicked = !isFilterClicked
                        }
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
                )
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
                                filter.foodName?.lowercase(Locale.getDefault())?.contains(userSearch)
                            )
                        }) {
                            FoodItem(data = it)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FoodItem(modifier: Modifier = Modifier, data: FoodCachePresentation) {
    Column(modifier = modifier.padding(8.dp)) {
        val singleInstanceImageLoader = LocalContext.current.imageLoader
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(data.foodImage)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .addHeader("Cache-Control", "max-age=20,public")
            .crossfade(true)
            .build()

        Image(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 10))
                .size(200.dp),
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(
                request = imageRequest,
                imageLoader = singleInstanceImageLoader
            ),
            contentDescription = "preview"
        )


        Text(
            text = data.foodName ?: "No food", style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )
    }

}
