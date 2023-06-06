package com.ian.junemon.foodiepedia.view.screen.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.FilterItem
import com.ian.junemon.foodiepedia.theme.MontserratFont

@Composable
fun HomeFilterChipSelectionContent(
    modifier: Modifier = Modifier,
    listFilterItem: List<FilterItem>,
    onFilterFoodSelected: (String) -> Unit
) {
    LazyRow(modifier = modifier,horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
                Text(text = singleItem.filterText, fontFamily = MontserratFont)
            }
        }
    }

}