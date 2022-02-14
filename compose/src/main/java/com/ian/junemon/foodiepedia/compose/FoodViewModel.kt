package com.ian.junemon.foodiepedia.compose

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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class FoodViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : ViewModel() {

    private val _food = MutableStateFlow(FoodUiState.initial())
    val food = _food.asStateFlow()

    private val _searchFood = MutableStateFlow("")
    val searchFood = _searchFood.asStateFlow()

    private val _selectedFoodId = MutableStateFlow(0)
    val selectedFoodId = _selectedFoodId.asStateFlow()

    private val _bookmarkedFood = MutableStateFlow(BookmarkedFoodUiState.initial())
    val bookmarkedFood = _bookmarkedFood.asStateFlow()

    private val _detailFood = MutableStateFlow(DetailFoodUiState.initial())
    val detailFood = _detailFood.asStateFlow()

    fun setSelectedFoodId(id: Int){
        _selectedFoodId.value = id
    }

    fun setSearchFood(query: String) {
        _searchFood.value = query
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
                when (result) {
                    is RepositoryData.Error -> _food.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            errorMessage = result.msg,
                            isLoading = false
                        )
                    }
                    is RepositoryData.Success -> {
                        _food.update { currentUiState ->
                            currentUiState.copy(
                                data = result.data.mapToCachePresentation(),
                                errorMessage = "",
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }

        viewModelScope.launch {
            useCase.getSavedDetailCache().collect { result ->
                when (result) {
                    is RepositoryData.Error -> _bookmarkedFood.update { currentUiState ->
                        currentUiState.copy(data = emptyList(), errorMessage = result.msg)
                    }
                    is RepositoryData.Success -> _bookmarkedFood.update { currentUiState ->
                        currentUiState.copy(data = result.data, errorMessage = "")
                    }
                }
            }
        }

        viewModelScope.launch {
            selectedFoodId.flatMapLatest {
                useCase.getCacheById(it)
            }.collect{ result ->
                when (result) {
                    is RepositoryData.Error -> _detailFood.update { currentUiState ->
                        currentUiState.copy(
                            data = null,
                            errorMessage = result.msg,
                        )
                    }
                    is RepositoryData.Success -> {
                        _detailFood.update { currentUiState ->
                            currentUiState.copy(
                                data = result.data.mapToCachePresentation(),
                                errorMessage = "",
                            )
                        }
                    }
                }
            }
        }
    }
}