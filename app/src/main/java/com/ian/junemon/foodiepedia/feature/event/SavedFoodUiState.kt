package com.ian.junemon.foodiepedia.feature.event

import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain


/**
 * Created by Ian Damping on 30,December,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
data class SavedFoodUiState(
    val data: List<SavedFoodCacheDomain>,
    val errorMessage: String
) {
    companion object {
        fun initial() = SavedFoodUiState(
            data = emptyList(),
            errorMessage = ""
        )
    }
}
