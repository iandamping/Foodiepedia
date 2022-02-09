package com.ian.junemon.foodiepedia.compose.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.imageLoader
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation


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
