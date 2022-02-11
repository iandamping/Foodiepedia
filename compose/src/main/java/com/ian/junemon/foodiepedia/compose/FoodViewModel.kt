package com.ian.junemon.foodiepedia.compose

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.compose.Constant.FILTER_0
import com.ian.junemon.foodiepedia.compose.state.DetailFoodUiState
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val useCase: FoodUseCase
) : BaseViewModel() {

    private val _searchFood = MutableStateFlow("")
    val searchFood = _searchFood.asStateFlow()

    private val _food = MutableStateFlow(FoodUiState.initial())
    val food = _food.asStateFlow()

    private val _detailFood = MutableStateFlow(DetailFoodUiState.initial())
    val detailFood = _detailFood.asStateFlow()

    fun setSearchFood(query: String) {
        _searchFood.value = query
    }

    fun getFoodById(id: Int) {
        consumeSuspend {
            useCase.getCacheById(id).collect { result ->
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

    fun filterData() = useCase.loadSharedPreferenceFilter().asLiveData()

    fun setFilterData(data: String) {
        viewModelScope.launch {
            useCase.setSharedPreferenceFilter(data)
        }
    }


    init {
        consumeSuspend {
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

    }
}