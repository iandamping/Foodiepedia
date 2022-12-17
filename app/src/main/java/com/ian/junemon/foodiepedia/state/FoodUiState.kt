package com.ian.junemon.foodiepedia.state

import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

data class FoodUiState(
    val isLoading: Boolean,
    val data: List<FoodCachePresentation>,
    val errorMessage: String
) {
    companion object {
        fun initial() = FoodUiState(
            isLoading = true,
            data = emptyList(),
            errorMessage = ""
        )
    }
}
