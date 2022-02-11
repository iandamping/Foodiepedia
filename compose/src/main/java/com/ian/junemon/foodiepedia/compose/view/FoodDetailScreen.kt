package com.ian.junemon.foodiepedia.compose.view

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.compose.R
import com.ian.junemon.foodiepedia.compose.state.DetailFoodUiState
import com.ian.junemon.foodiepedia.compose.ui.theme.CalmWhite
import com.ian.junemon.foodiepedia.compose.ui.theme.GrayIconButton
import com.ian.junemon.foodiepedia.compose.ui.theme.YellowFood
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

@Composable
fun FoodDetailScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    data: DetailFoodUiState,
    shareData: (FoodCachePresentation) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        when {
            data.data != null -> {
                val singleInstanceImageLoader = LocalContext.current.imageLoader
                val imageRequest = ImageRequest.Builder(LocalContext.current)
                    .data(data.data.foodImage)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .addHeader("Cache-Control", "max-age=20,public")
                    .crossfade(true)
                    .build()

                ConstraintLayout(
                    modifier = modifier.fillMaxSize()
                ) {
                    val (backButton, imageHeader, shareItem, bookmarkItem, customView) = createRefs()

                    Image(
                        painter = rememberImagePainter(
                            request = imageRequest,
                            imageLoader = singleInstanceImageLoader
                        ),
                        contentDescription = "header",
                        modifier = Modifier
                            .height(300.dp)
                            .fillMaxWidth()
                            .background(YellowFood)
                            .constrainAs(imageHeader) {
                                top.linkTo(parent.top)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
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
                                top.linkTo(parent.top, margin = 45.dp)
                                start.linkTo(parent.start, margin = 8.dp)
                            },
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_back_black_24dp),
                            contentDescription = "Search"
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(GrayIconButton.copy(alpha = 0.6f))
                            .constrainAs(shareItem) {
                                end.linkTo(parent.end, margin = 8.dp)
                                centerVerticallyTo(parent, bias = 0.3f)
                            },
                        onClick = {
                            shareData.invoke(data.data)
                        }
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_share_white_24dp),
                            contentDescription = "Search"
                        )
                    }

                    IconButton(
                        modifier = Modifier
                            .size(45.dp)
                            .clip(CircleShape)
                            .background(GrayIconButton.copy(alpha = 0.6f))
                            .constrainAs(bookmarkItem) {
                                top.linkTo(customView.top)
                                bottom.linkTo(customView.top)
                                end.linkTo(parent.end, margin = 8.dp)
                            },
                        onClick = {}
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_unbookmark),
                            contentDescription = "Search"
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .background(CalmWhite)
                        .fillMaxSize()
                        .padding(8.dp)
                ) {

                    Text(
                        text = data.data.foodName ?: "",
                        style = MaterialTheme.typography.h3.copy(color = YellowFood)
                    )

                    Text(
                        text = data.data.foodDescription ?: "",
                        style = MaterialTheme.typography.body2.copy(textAlign = TextAlign.Justify)
                    )
                }
            }

            data.errorMessage.isNotEmpty() -> {
                Text(text = "Empty Data")
            }
        }
    }


}