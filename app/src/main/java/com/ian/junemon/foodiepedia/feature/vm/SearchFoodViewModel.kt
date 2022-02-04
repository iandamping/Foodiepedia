package com.ian.junemon.foodiepedia.feature.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.feature.event.CategorizeFoodUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


/**
 * Created by Ian Damping on 30,December,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@HiltViewModel
class SearchFoodViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: FoodUseCase
) :
    BaseViewModel() {

    private val _searchFoodCache = MutableStateFlow(CategorizeFoodUiState.initial())
    val searchFoodCache = _searchFoodCache.asStateFlow()

    val filteredData: LiveData<String> =
        savedStateHandle.getLiveData<String>("query")


    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }


    init {
        consumeSuspend {
            repository.getCache().collect { result ->
                when (result) {
                    is RepositoryData.Error -> _searchFoodCache.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            errorMessage = result.msg
                        )
                    }
                    is RepositoryData.Success -> {
                        _searchFoodCache.update { currentUiState ->
                            currentUiState.copy(
                                errorMessage = "",
                                data = result.data
                            )
                        }
                    }
                }
            }
        }


        searchFood(savedStateHandle.get<String>("query"))
    }

    fun searchFood(newText: String?) {
        viewModelScope.launch {
            repository.getCache().collect { result ->
                when (result) {
                    is RepositoryData.Error -> _searchFoodCache.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            errorMessage = result.msg
                        )
                    }
                    is RepositoryData.Success -> {
                        _searchFoodCache.update { currentUiState ->
                            currentUiState.copy(
                                errorMessage = "",
                                data = if (newText.isNullOrEmpty()) {
                                    result.data
                                } else result.data.filter {
                                    it.foodName?.lowercase(Locale.getDefault())
                                        ?.contains(newText)!!
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}