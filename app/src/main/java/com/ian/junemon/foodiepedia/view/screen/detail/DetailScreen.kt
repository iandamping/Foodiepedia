package com.ian.junemon.foodiepedia.view.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabasePresentation
import com.ian.junemon.foodiepedia.theme.CalmWhite
import com.ian.junemon.foodiepedia.theme.GrayIconButton
import com.ian.junemon.foodiepedia.theme.YellowFood
import com.ian.junemon.foodiepedia.viewmodel.FoodDetailViewModel

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: FoodDetailViewModel = hiltViewModel(),
    shareData: (FoodCachePresentation) -> Unit,
    navigateUp: () -> Unit
) {
    val bookmarkedFood = viewModel.uiBookmarkFoodState
    val data = viewModel.uiDetailFoodState

    val idForBookmarkedItem: MutableState<Int?> = remember {
        mutableStateOf(null)
    }

    LazyColumn(modifier = modifier
        .fillMaxSize()
        .background(CalmWhite)) {
        when {
            data.data != null -> {
                item {
                    val imageRequest = ImageRequest.Builder(LocalContext.current)
                        .data(data.data.foodImage)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .addHeader("Cache-Control", "max-age=20,public")
                        .crossfade(true)
                        .build()

                    Box {

                        AsyncImage(
                            model = imageRequest,
                            contentDescription = stringResource(R.string.detail_header),
                            modifier = Modifier
                                .background(YellowFood)
                                .height(350.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.ic_placeholder),
                            error = painterResource(id = R.drawable.ic_error)
                        )

                        IconButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(45.dp)
                                .clip(CircleShape)
                                .background(YellowFood)
                                .align(Alignment.TopStart),
                            onClick = {
                                navigateUp.invoke()
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_arrow_back_black_24dp),
                                contentDescription = stringResource(R.string.back_to_home)
                            )
                        }

                        Box(
                            modifier = Modifier
                                .height(60.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                                .background(CalmWhite)
                                .align(Alignment.BottomCenter)
                        ) {
                            Row(
                                modifier = Modifier
                                    .align(Alignment.BottomEnd)
                                    .fillParentMaxHeight()
                                    .padding(end = 8.dp, top = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                            ) {
                                IconButton(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(CircleShape)
                                        .background(GrayIconButton.copy(alpha = 0.6f)),
                                    onClick = {
                                        shareData.invoke(data.data)
                                    }
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_share_white_24dp),
                                        contentDescription = stringResource(R.string.share_content)
                                    )
                                }


                                IconButton(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .clip(CircleShape)
                                        .background(GrayIconButton.copy(alpha = 0.6f)),
                                    onClick = {
                                        if (idForBookmarkedItem.value != null) {
                                            viewModel.unbookmarkFood(idForBookmarkedItem.value!!)
                                        } else {
                                            idForBookmarkedItem.value = null
                                            viewModel.bookmarkFood(data.data.mapToDetailDatabasePresentation())
                                        }
                                    }
                                ) {

                                    val tmp =
                                        bookmarkedFood.data.filter { result -> result.foodName == data.data.foodName }

                                    if (tmp.isNotEmpty()) {
                                        tmp.forEach { savedFood ->
                                            if (savedFood.foodName == data.data.foodName) {
                                                idForBookmarkedItem.value = savedFood.localFoodID
                                                Image(
                                                    painter = painterResource(
                                                        id = R.drawable.ic_bookmarked
                                                    ),
                                                    contentDescription = stringResource(R.string.item_already_bookmarked)
                                                )
                                            }
                                        }
                                    } else {
                                        Image(
                                            painter = painterResource(
                                                id = R.drawable.ic_unbookmark
                                            ),
                                            contentDescription = stringResource(R.string.item_is_not_bookmarked)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
                item {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp),
                        text = data.data.foodName ?: stringResource(R.string.food_has_no_name),
                        style = MaterialTheme.typography.h3.copy(color = YellowFood)
                    )
                }

                item {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, end = 8.dp),
                        text = data.data.foodDescription
                            ?: stringResource(R.string.food_has_no_description),
                        style = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Justify)
                    )
                }
            }

            data.errorMessage.isNotEmpty() -> {
                item {
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(R.string.no_data_available)
                    )
                }

            }
        }
    }
}