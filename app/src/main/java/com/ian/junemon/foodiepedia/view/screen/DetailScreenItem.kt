package com.ian.junemon.foodiepedia.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

@Composable
fun DetailScreenItem(
    modifier: Modifier = Modifier,
    data: FoodCachePresentation,
    selectedFood: (FoodCachePresentation) -> Unit
) {
    Column(modifier = modifier
        .padding(8.dp)
        .clickable {
            selectedFood(data)
        }) {
        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(data.foodImage)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .addHeader("Cache-Control", "max-age=20,public")
            .crossfade(true)
            .build()

        AsyncImage(
            modifier = Modifier
                .clip(RoundedCornerShape(percent = 10))
                .size(200.dp),
            contentScale = ContentScale.Crop,
            model = imageRequest,
            contentDescription = stringResource(R.string.detail_image),
            placeholder = painterResource(id = R.drawable.ic_placeholder),
            error = painterResource(id = R.drawable.ic_error)
        )


        Text(
            text = data.foodName ?: stringResource(R.string.food_has_no_name), style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Bold
        )
    }

}
