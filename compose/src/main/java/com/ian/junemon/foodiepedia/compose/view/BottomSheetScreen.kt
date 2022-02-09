package com.ian.junemon.foodiepedia.compose.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ian.junemon.foodiepedia.compose.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheetScreen(
    modifier: Modifier,
    state: ModalBottomSheetState,
    scope: CoroutineScope,
    filterFood: String,
    setFilterFood: (String) -> Unit,
    content: @Composable () -> Unit
) {
    ModalBottomSheetLayout(modifier = modifier, sheetState = state, sheetContent = {
        Column(modifier = Modifier.fillMaxWidth()) {
            IconButton(modifier = Modifier
                .padding(vertical = 8.dp)
                .align(Alignment.End), onClick = {
                scope.launch {
                    if (state.isVisible) {
                        state.hide()
                    }
                }
            }, content = {
                Image(imageVector = Icons.Default.Close, contentDescription = "Close Item")
            })

            LazyColumn {
                items(Constant.configureFilterItem(filterFood)) { singleItem ->
                    FilterFoodDialog(filterItem = singleItem, selectedFilterItem = {
                        scope.launch {
                            if (state.isVisible) {
                                state.hide()
                            }
                        }
                        setFilterFood(it.filterText)
                    })
                }
            }
        }
    }, content = content)
}