package com.ian.junemon.foodiepedia.state

import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

data class FoodOfTheDayUiState(
    val data: List<FoodCachePresentation>,
    val errorMessage: String
) {
    companion object {
        fun initial() = FoodOfTheDayUiState(
            data = emptyList(),
            errorMessage = ""
        )
    }
}