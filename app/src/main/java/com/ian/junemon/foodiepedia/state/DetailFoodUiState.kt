package com.ian.junemon.foodiepedia.state

import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation

data class DetailFoodUiState(
    val data: FoodCachePresentation?,
    val errorMessage: String
) {
    companion object {
        fun initial() = DetailFoodUiState(
            data = null,
            errorMessage = ""
        )
    }
}