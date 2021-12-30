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
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.feature.event.CategorizeFoodUiState
import com.ian.junemon.foodiepedia.feature.event.FoodUiState
import com.ian.junemon.foodiepedia.feature.event.SavedFoodUiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModel @Inject constructor(private val repository: FoodUseCase) : BaseViewModel() {
    private val _foodCache = MutableStateFlow(FoodUiState.initial())
    val foodCache = _foodCache.asStateFlow()


    private val _savedFood = MutableStateFlow(SavedFoodUiState.initial())
    val savedFood = _savedFood.asStateFlow()

    private val _searchItem: MutableLiveData<List<FoodCachePresentation>> = MutableLiveData()
    val searchItem: LiveData<List<FoodCachePresentation>> = _searchItem

    private val _foodData: MutableLiveData<FoodRemoteDomain> = MutableLiveData()
    val foodData: LiveData<FoodRemoteDomain> = _foodData

    private val _foodImageUri: MutableLiveData<Uri> = MutableLiveData()
    val foodImageUri: LiveData<Uri> = _foodImageUri

    val etFoodName: MutableLiveData<String> = MutableLiveData()
    val etFoodArea: MutableLiveData<String> = MutableLiveData()
    val etFoodDescription: MutableLiveData<String> = MutableLiveData()

    inline fun observingEditText(
        lifecycleOwner: LifecycleOwner,
        liveData: LiveData<String>,
        crossinline data: (String) -> Unit
    ) {
        liveData.observe(lifecycleOwner, {
            data.invoke(it)
        })
    }

    init {
        consumeSuspend {
            repository.prefetchData().collect { result ->
                when (result) {
                    is RepositoryData.Error -> Timber.e("error : ${result.msg}")
                    is RepositoryData.Success -> repository.setCache(*result.data.toTypedArray())

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

    fun getFood() {
        viewModelScope.launch {
            repository.loadSharedPreferenceFilter().flatMapLatest {
                when{
                    it.isEmpty() -> repository.getCache()
                    it == noFilterValue -> repository.getCache()
                    else -> repository.getCategorizeCache(it)
                }
            }.debounce(100).collect { result ->
                when (result) {
                    is RepositoryData.Error -> _foodCache.update { currentUiState ->
                        currentUiState.copy(
                            data = emptyList(),
                            errorMessage = result.msg,
                            isLoading = false
                        )
                    }
                    is RepositoryData.Success -> _foodCache.update { currentUiState ->
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