package com.ian.junemon.foodiepedia.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.state.FoodOfTheDayUiState
import com.ian.junemon.foodiepedia.state.FoodUiState
import com.ian.junemon.foodiepedia.util.Constant.FILTER_0
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : ViewModel() {

    var uiFoodState by mutableStateOf(FoodUiState.initial())
        private set

    var uiFoodOfTheDayState by mutableStateOf(FoodOfTheDayUiState.initial())
        private set

    var uiSearchFoodState by mutableStateOf("")
        private set

    var uiBookmarkFoodState by mutableStateOf(BookmarkedFoodUiState.initial())
        private set



    fun setSearchFood(query: String) {
        uiSearchFoodState = query
    }

    fun filterData() = useCase.loadSharedPreferenceFilter().asLiveData()

    fun setFilterData(data: String) {
            useCase.setSharedPreferenceFilter(data)
    }


    init {
        viewModelScope.launch {
            useCase.loadSharedPreferenceFilter().flatMapLatest {
                when {
                    it.isEmpty() -> useCase.prefetchData()
                    it == FILTER_0 -> useCase.prefetchData()
                    else -> useCase.getCategorizeCache(it)
                }
            }.collect { result ->
                uiFoodState = when (result) {
                    is RepositoryData.Error ->
                        uiFoodState.copy(
                            data = emptyList(),
                            errorMessage = result.msg,
                            isLoading = false
                        )
                    is RepositoryData.Success ->
                        uiFoodState.copy(
                            data = result.data.mapToCachePresentation(),
                            errorMessage = "",
                            isLoading = false
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

        viewModelScope.launch {
            useCase.getFoodOfTheDay().collect { result ->
                uiFoodOfTheDayState = when (result) {
                    is RepositoryData.Error ->
                        uiFoodOfTheDayState.copy(data = emptyList(), errorMessage = result.msg)

                    is RepositoryData.Success -> {
                        Log.e("data", "${result.data.map { it.foodName }}" )
                        uiFoodOfTheDayState.copy(
                            data = result.data.mapToCachePresentation(),
                            errorMessage = ""
                        )
                    }

                }
            }
        }
    }
}