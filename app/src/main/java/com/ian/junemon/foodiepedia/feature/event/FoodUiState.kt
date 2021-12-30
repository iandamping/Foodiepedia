package com.ian.junemon.foodiepedia.feature.event

import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain


/**
 * Created by Ian Damping on 30,December,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class FoodUiState(
    val isLoading: Boolean,
    val data: List<FoodCacheDomain>,
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
