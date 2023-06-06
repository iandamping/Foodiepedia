package com.ian.junemon.foodiepedia.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.*
import com.ian.junemon.foodiepedia.util.Constant

@Composable
fun LottieLoading(modifier: Modifier = Modifier, isLoading: Boolean) {
    val composition by rememberLottieComposition(spec = LottieCompositionSpec.Asset(Constant.KNIFE_LOADING))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
    )

    if (isLoading) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(composition, progress, Modifier.size(200.dp))
        }
    }


}