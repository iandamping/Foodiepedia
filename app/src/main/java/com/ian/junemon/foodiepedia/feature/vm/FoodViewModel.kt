package com.ian.junemon.foodiepedia.feature.vm

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.feature.util.Event
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.WorkerResult
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import com.junemon.model.domain.SavedFoodCacheDomain
import com.junemon.model.presentation.FoodCachePresentation
import kotlinx.coroutines.launch
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

    private val _eventDissmissFromInput = MutableLiveData<Event<Unit>>()
    val eventDissmissFromInput: LiveData<Event<Unit>> = _eventDissmissFromInput

    val etFoodName: MutableLiveData<String> = MutableLiveData()
    val etFoodArea: MutableLiveData<String> = MutableLiveData()
    val etFoodDescription: MutableLiveData<String> = MutableLiveData()

    inline fun observingEditText(
        lifecycleOwner: LifecycleOwner,
        liveData: LiveData<String>,
        crossinline data: (String) -> Unit
    ) {
        liveData.observe(lifecycleOwner, Observer {
            data.invoke(it)
        })
    }

    fun eventDissmissFromInput() {
        _eventDissmissFromInput.value = Event(Unit)
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

    fun getSavedDetailCache() = repository.getSavedDetailCache()

    fun deleteSelectedId(selectedId: Int) {
        viewModelScope.launch {
            repository.deleteSelectedId(selectedId)
        }
    }

    fun foodPrefetch(): LiveData<Results<List<FoodCacheDomain>>> = repository.homeFoodPrefetch()


    fun getCache(): LiveData<List<FoodCacheDomain>> = repository.getCache()

    fun uploadFirebaseData(
        data: FoodRemoteDomain,
        imageUri: Uri
    ): LiveData<FirebaseResult<Nothing>> = repository.uploadFirebaseData(data, imageUri)

    fun loadSharedPreferenceFilter():String = repository.loadSharedPreferenceFilter()

    fun setSharedPreferenceFilter(data:String) = repository.setSharedPreferenceFilter(data)
}