package com.ian.junemon.foodiepedia.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.util.Constant
import com.ian.junemon.foodiepedia.view.FilterFoodDialog
import com.ian.junemon.foodiepedia.viewmodel.FoodViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodViewModel = hiltViewModel(),
    scope: CoroutineScope = rememberCoroutineScope(),
    selectFood: (Int) -> Unit,
) {
    val userSearchFood = viewModel.uiSearchFoodState
    val filterFood by viewModel.filterData().observeAsState(initial = "")
    val foodState = viewModel.uiFoodState

    val state = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden
    )

    ModalBottomSheetLayout(modifier = modifier, sheetState = state, sheetContent = {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.filter_food),
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(modifier = Modifier
                    .padding(vertical = 8.dp), onClick = {
                    scope.launch {
                        if (state.isVisible) {
                            state.hide()
                        }
                    }
                }, content = {
                    Image(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.close_item)
                    )
                })
            }


            LazyColumn {
                items(Constant.configureFilterItem(filterFood)) { singleItem ->
                    FilterFoodDialog(filterItem = singleItem, selectedFilterItem = { filterItem ->
                        scope.launch {
                            if (state.isVisible) {
                                state.hide()
                            }
                        }
                        viewModel.setFilterData(filterItem.filterText)
                    })
                }
            }
        }
    }, content = {
        HomeScreenContent(
            foodState = foodState,
            state = state,
            userSearch = userSearchFood,
            onFoodSearch = { query ->
                viewModel.setSearchFood(query)
            },
            onContentSelectedFood = { id ->
                selectFood.invoke(id)
            })
    })
}