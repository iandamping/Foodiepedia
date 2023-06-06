package com.ian.junemon.foodiepedia.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.navigation.ScreensNavigationArgument
import com.ian.junemon.foodiepedia.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.state.DetailFoodUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: FoodUseCase
) : ViewModel() {

    private val detailFoodId =
        savedStateHandle.getStateFlow(ScreensNavigationArgument.DetailFood.name, 0)

    var uiDetailFoodState by mutableStateOf(DetailFoodUiState.initial())
        private set
    var uiBookmarkFoodState by mutableStateOf(BookmarkedFoodUiState.initial())
        private set

    fun bookmarkFood(data: SavedFoodCacheDomain) {
        useCase.setCacheDetailFood(data)
    }

    fun unbookmarkFood(selectedId: Int) {
        useCase.deleteSelectedId(selectedId)
    }

    init {
        viewModelScope.launch {
            detailFoodId.flatMapLatest {
                useCase.getCacheById(it)
            }.collect { result ->
                uiDetailFoodState = when (result) {
                    is RepositoryData.Error ->
                        uiDetailFoodState.copy(
                            data = null,
                            errorMessage = result.msg
                        )

                    is RepositoryData.Success ->
                        uiDetailFoodState.copy(
                            data = result.data.mapToCachePresentation(),
                            errorMessage = ""
                        )

                }
            }
        }

        viewModelScope.launch {
            useCase.getSavedDetailCache().collect { result ->
                uiBookmarkFoodState = when (result) {
                    is RepositoryData.Error ->
                        uiBookmarkFoodState.copy(data = emptyList(), errorMessage = result.msg)

                    is RepositoryData.Success ->
                        uiBookmarkFoodState.copy(data = result.data, errorMessage = "")

                }
            }
        }
    }
}