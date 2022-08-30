package com.ian.junemon.foodiepedia.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.compose.Constant.FILTER_0
import com.ian.junemon.foodiepedia.compose.state.BookmarkedFoodUiState
import com.ian.junemon.foodiepedia.compose.state.DetailFoodUiState
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FoodViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : ViewModel() {

    var uiFoodState by mutableStateOf(FoodUiState.initial())
        private set
    var uiBookmarkFoodState by mutableStateOf(BookmarkedFoodUiState.initial())
        private set
    var uiDetailFoodState by mutableStateOf(DetailFoodUiState.initial())
        private set
    var uiSearchFoodState by mutableStateOf("")
        private set

    private val _selectedFoodId = MutableStateFlow(0)
    val selectedFoodId = _selectedFoodId.asStateFlow()


    fun setSelectedFoodId(id: Int) {
        _selectedFoodId.value = id
    }

    fun setSearchFood(query: String) {
        uiSearchFoodState = query
    }

    fun bookmarkFood(data: SavedFoodCacheDomain) {
        viewModelScope.launch {
            useCase.setCacheDetailFood(data)
        }
    }

    fun unbookmarkFood(selectedId: Int) {
        viewModelScope.launch {
            useCase.deleteSelectedId(selectedId)
        }
    }

    fun filterData() = useCase.loadSharedPreferenceFilter().asLiveData()

    fun setFilterData(data: String) {
        viewModelScope.launch {
            useCase.setSharedPreferenceFilter(data)
        }
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
            selectedFoodId.flatMapLatest {
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
    }
}