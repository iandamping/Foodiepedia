package com.ian.junemon.foodiepedia.state

import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain

data class BookmarkedFoodUiState(
    val data: List<SavedFoodCacheDomain>,
    val errorMessage: String
) {
    companion object {
        fun initial() = BookmarkedFoodUiState(
            data = emptyList(),
            errorMessage = ""
        )
    }
}
