package com.ian.junemon.foodiepedia.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.viewmodel.FoodDetailViewModel
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.mapToDetailDatabasePresentation
import com.ian.junemon.foodiepedia.theme.CalmWhite
import com.ian.junemon.foodiepedia.theme.GrayIconButton
import com.ian.junemon.foodiepedia.theme.YellowFood

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


    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
    ) {
        val (backButton, imageHeader, shareItem, bookmarkItem, customView, mainItemRef, errorRef) = createRefs()
        when {
            data.data != null -> {
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(data.data.foodImage)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .addHeader("Cache-Control", "max-age=20,public")
                    .crossfade(true)
                    .build()

                val imageHorizontalGuideLine = createGuidelineFromTop(0.45f)
                val navigateUpHorizontalGuideLine = createGuidelineFromTop(0.02f)


                Column(
                    modifier = Modifier
                        .background(CalmWhite)
                        .padding(8.dp)
                        .constrainAs(mainItemRef) {
                            top.linkTo(imageHorizontalGuideLine)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                            height = Dimension.fillToConstraints
                            width = Dimension.fillToConstraints
                        }
                        .verticalScroll(rememberScrollState())
                ) {

                    Text(
                        text = data.data.foodName ?: stringResource(R.string.food_has_no_name),
                        style = MaterialTheme.typography.h3.copy(color = YellowFood)
                    )

                    Text(
                        text = data.data.foodDescription ?: stringResource(R.string.food_has_no_description),
                        style = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Justify)
                    )
                }


                AsyncImage(
                    model = imageRequest,
                    contentDescription = stringResource(R.string.detail_header),
                    modifier = Modifier
                        .background(YellowFood)
                        .constrainAs(imageHeader) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(imageHorizontalGuideLine)
                            width = Dimension.fillToConstraints
                            height = Dimension.fillToConstraints
                        }
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    error = painterResource(id = R.drawable.ic_error)
                )

                Box(
                    modifier = Modifier
                        .height(30.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(CalmWhite)
                        .constrainAs(customView) {
                            bottom.linkTo(imageHeader.bottom)
                        }
                )

                IconButton(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(YellowFood)
                        .constrainAs(backButton) {
                            top.linkTo(navigateUpHorizontalGuideLine)
                            start.linkTo(parent.start, margin = 8.dp)
                        },
                    onClick = {
                        navigateUp.invoke()
                    }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_back_black_24dp),
                        contentDescription = stringResource(R.string.back_to_home)
                    )
                }

                IconButton(
                    modifier = Modifier
                        .size(45.dp)
                        .clip(CircleShape)
                        .background(GrayIconButton.copy(alpha = 0.6f))
                        .constrainAs(shareItem) {

                            top.linkTo(customView.bottom)
                            bottom.linkTo(customView.bottom)
                            end.linkTo(bookmarkItem.start, margin = 16.dp)
                        },
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
                        .background(GrayIconButton.copy(alpha = 0.6f))
                        .constrainAs(bookmarkItem) {
                            top.linkTo(customView.bottom)
                            bottom.linkTo(customView.bottom)
                            end.linkTo(parent.end, margin = 16.dp)
                        },
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

            data.errorMessage.isNotEmpty() -> {
                Text(modifier = Modifier.constrainAs(errorRef) {
                    centerHorizontallyTo(parent)
                    centerVerticallyTo(parent)
                }, text = stringResource(R.string.no_data_available))
            }
        }
    }

}