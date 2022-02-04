package com.ian.junemon.foodiepedia.feature.vm

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.RepositoryData
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.feature.event.FoodUiState
import com.ian.junemon.foodiepedia.feature.event.SavedFoodUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@HiltViewModel
class FoodViewModel @Inject constructor(private val repository: FoodUseCase) : BaseViewModel() {
    private val _foodCache = MutableStateFlow(FoodUiState.initial())
    val foodCache = _foodCache.asStateFlow()

    private val _savedFood = MutableStateFlow(SavedFoodUiState.initial())
    val savedFood = _savedFood.asStateFlow()

    private val _foodData: MutableLiveData<FoodRemoteDomain> = MutableLiveData()
    val foodData: LiveData<FoodRemoteDomain> = _foodData

    private val _foodImageUri: MutableLiveData<Uri> = MutableLiveData()
    val foodImageUri: LiveData<Uri> = _foodImageUri

    val etFoodName: MutableLiveData<String> = MutableLiveData()
    val etFoodArea: MutableLiveData<String> = MutableLiveData()
    val etFoodDescription: MutableLiveData<String> = MutableLiveData()

    companion object {
        private val CACHE_EXPIRY = TimeUnit.HOURS.toMillis(1)
    }

    private fun Long.isExpired(): Boolean = (System.currentTimeMillis() - this) > CACHE_EXPIRY


    inline fun observingEditText(
        lifecycleOwner: LifecycleOwner,
        liveData: LiveData<String>,
        crossinline data: (String) -> Unit
    ) {
        liveData.observe(lifecycleOwner) {
            data.invoke(it)
        }
    }

    init {
        consumeSuspend {
            repository.loadSharedPreferenceFilter().flatMapLatest {
                when {
                    it.isEmpty() -> repository.prefetchData()
                    it == noFilterValue -> repository.prefetchData()
                    else -> repository.getCategorizeCache(it)
                }
            }.collect { result ->
                when (result) {
                    is RepositoryData.Error -> _foodCache.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            errorMessage = result.msg,
                            isLoading = false
                        )
                    }
                    is RepositoryData.Success ->
                        _foodCache.update { currentUiState ->
                            currentUiState.copy(
                                data = result.data,
                                errorMessage = "",
                                isLoading = false
                            )
                        }
                }
            }
        }
    }


    fun setFood(data: FoodRemoteDomain) {
        _foodData.value = data
    }

    fun setFoodUri(uri: Uri) {
        _foodImageUri.value = uri
    }


    fun setCacheDetailFood(data: SavedFoodCacheDomain) {
        viewModelScope.launch {
            repository.setCacheDetailFood(data)
        }
    }

    fun getSavedDetailCache() {
        viewModelScope.launch {
            repository.getSavedDetailCache().collect { result ->
                when (result) {
                    is RepositoryData.Error -> _savedFood.update { currentUiState ->
                        currentUiState.copy(data = emptyList(), errorMessage = result.msg)
                    }
                    is RepositoryData.Success -> _savedFood.update { currentUiState ->
                        currentUiState.copy(data = result.data, errorMessage = "")
                    }
                }
            }
        }
    }

    fun deleteSelectedId(selectedId: Int) {
        viewModelScope.launch {
            repository.deleteSelectedId(selectedId)
        }
    }

    fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> = repository.uploadFirebaseData(data, imageUri)

    fun loadSharedPreferenceFilter(): LiveData<String> =
        repository.loadSharedPreferenceFilter().asLiveData(viewModelScope.coroutineContext)

    fun setSharedPreferenceFilter(data: String) {
        viewModelScope.launch {
            repository.setSharedPreferenceFilter(data)
        }
    }
}