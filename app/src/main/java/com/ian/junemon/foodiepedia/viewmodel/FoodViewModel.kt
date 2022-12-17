package com.ian.junemon.foodiepedia.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
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

    var uiSearchFoodState by mutableStateOf("")
        private set


    fun setSearchFood(query: String) {
        uiSearchFoodState = query
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
    }
}