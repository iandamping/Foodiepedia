package com.ian.junemon.foodiepedia.compose

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.compose.Constant.FILTER_0
import com.ian.junemon.foodiepedia.compose.state.FoodUiState
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val useCase: FoodUseCase
) : BaseViewModel() {

    private val _searchFood = MutableStateFlow("")
    val searchFood = _searchFood.asStateFlow()

    private val _food = MutableStateFlow(FoodUiState.initial())
    val food = _food.asStateFlow()

    val savedStateQueryData: LiveData<String> =
        savedStateHandle.getLiveData<String>("query")

    fun setQuery(query: String) {
        savedStateHandle["query"] = query
    }

    fun setSearchFood(query: String) {
        _searchFood.value = query
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

//        consumeSuspend {
//            searchFood.collect{
//                Timber.e("result : $it")
//            }
//        }

//        consumeSuspend {
//            useCase.getCache().collect{ result ->
//                when (result) {
//                    is RepositoryData.Error -> _food.update { currentUiState ->
//                        currentUiState.copy(
//                            data = emptyList(),
//                            errorMessage = result.msg,
//                            isLoading = false
//                        )
//                    }
//                    is RepositoryData.Success -> {
//
//                        _food.update { currentUiState ->
//                            currentUiState.copy(
//                                data =  if (searchFood.value.isEmpty()) {
//                                    result.data.mapToCachePresentation()
//                                } else result.data.mapToCachePresentation().filter {
//                                    it.foodName?.lowercase(Locale.getDefault())
//                                        ?.contains(searchFood.value)!!
//                                },
//                                errorMessage = "",
//                                isLoading = false
//                            )
//                        }
//                    }
//                }
//            }
//        }

    }
}