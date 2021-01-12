package com.ian.junemon.foodiepedia.feature.vm

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.domain.GenericPairData
import com.ian.junemon.foodiepedia.core.domain.model.domain.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.util.Event
import com.ian.junemon.foodiepedia.core.util.DataConstant
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModel @Inject constructor(private val repository: FoodUseCase) : BaseViewModel() {
    private val _searchItem: MutableLiveData<MutableList<FoodCachePresentation>> = MutableLiveData()
    val searchItem: LiveData<MutableList<FoodCachePresentation>> = _searchItem

    private val _foodData: MutableLiveData<FoodRemoteDomain> = MutableLiveData()
    val foodData: LiveData<FoodRemoteDomain> = _foodData

    private val _foodImageUri: MutableLiveData<Uri> = MutableLiveData()
    val foodImageUri: LiveData<Uri> = _foodImageUri

    private val _moveToDetailFragmentEvent = MutableLiveData<Event<String>>()
    val moveToDetailFragmentEvent: LiveData<Event<String>> = _moveToDetailFragmentEvent

    private val _moveToProfileFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveToProfileFragmentEvent: LiveData<Event<Unit>> = _moveToProfileFragmentEvent

    private val _moveToSearchFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveToSearchFragmentEvent: LiveData<Event<Unit>> = _moveToSearchFragmentEvent

    private val _moveDetailToHomeFragmentEvent = MutableLiveData<Event<Unit>>()
    val moveDetailToHomeFragmentEvent: LiveData<Event<Unit>> = _moveDetailToHomeFragmentEvent

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

    fun moveToDetailFragment(foodValue: String) {
        _moveToDetailFragmentEvent.value = Event(foodValue)
    }

    fun moveToProfileFragment() {
        _moveToProfileFragmentEvent.value = Event(Unit)
    }

    fun moveToSearchFragmentEvent() {
        _moveToSearchFragmentEvent.value = Event(Unit)
    }

    fun moveDetailToHomeFragment() {
        _moveDetailToHomeFragmentEvent.value = Event(Unit)
    }

    fun setFood(data: FoodRemoteDomain) {
        _foodData.value = data
    }

    fun setFoodUri(uri: Uri) {
        _foodImageUri.value = uri
    }

    fun setSearchItem(data: MutableList<FoodCachePresentation>) {
        this._searchItem.value = data
    }

    fun setCacheDetailFood(data: SavedFoodCacheDomain) {
        viewModelScope.launch {
            repository.setCacheDetailFood(data)
        }
    }

    fun getSavedDetailCache() = repository.getSavedDetailCache().asLiveData(viewModelScope.coroutineContext)

    fun deleteSelectedId(selectedId: Int) {
        viewModelScope.launch {
            repository.deleteSelectedId(selectedId)
        }
    }

    fun getFoodBasedOnFilter() = repository.loadSharedPreferenceFilter().flatMapLatest {
        if (it.isEmpty()){
            repository.getCategorizeCache(DataConstant.filterValueBreakfast)
        } else{
            repository.getCategorizeCache(it)
        }
    }.asLiveData(viewModelScope.coroutineContext)

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repository.getCache().asLiveData(viewModelScope.coroutineContext)


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