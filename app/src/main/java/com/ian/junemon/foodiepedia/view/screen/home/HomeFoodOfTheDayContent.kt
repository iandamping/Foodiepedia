package com.ian.junemon.foodiepedia.view.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.state.FoodOfTheDayUiState
import com.ian.junemon.foodiepedia.theme.MontserratFont

@Composable
fun HomeFoodOfTheDayContent(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState,
    foodOfTheDaysUiState: FoodOfTheDayUiState,
    userSearch: String,
) {
    if (userSearch.isEmpty()) {
        Text(
            modifier = Modifier.padding(bottom = 8.dp),
            text = stringResource(R.string.today_best_choice),
            style = MaterialTheme.typography.h5,
            fontFamily = MontserratFont
        )

        when {
            foodOfTheDaysUiState.errorMessage.isNotEmpty() -> {
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

            foodOfTheDaysUiState.data.isNotEmpty() -> {
                LazyRow(
                    modifier = modifier,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    state = lazyListState,
                    flingBehavior = rememberSnapFlingBehavior(
                        lazyListState = lazyListState
                    )
                ) {
                    items(foodOfTheDaysUiState.data) { singleItem ->
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(percent = 10))
                                .size(height = 250.dp, width = 325.dp),
                            contentScale = ContentScale.Crop,
                            model = singleItem.foodImage,
                            contentDescription = stringResource(R.string.detail_image),
                            placeholder = painterResource(id = R.drawable.ic_placeholder),
                            error = painterResource(id = R.drawable.ic_error)
                        )
                    }
                }
            }
        }
    }

}