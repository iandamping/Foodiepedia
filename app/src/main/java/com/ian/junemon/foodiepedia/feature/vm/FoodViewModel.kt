package com.ian.junemon.foodiepedia.feature.vm

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ian.junemon.foodiepedia.core.domain.model.FoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.SavedFoodCacheDomain
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.Prefetch
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.model.DataEvent
import com.ian.junemon.foodiepedia.model.Event
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _dataEvent: MutableLiveData<Event<DataEvent>> = MutableLiveData()
    val dataEvent: LiveData<Event<DataEvent>> = _dataEvent

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

    fun setDataEvent(data:DataEvent){
        _dataEvent.value = Event(data)
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

    fun getFood() = repository.loadSharedPreferenceFilter().flatMapLatest {
        if (it.isEmpty()){
            repository.getCache()
        } else{
            if (it == noFilterValue){
                repository.getCache()
            }else {
                repository.getCategorizeCache(it)
            }
        }
    }.asLiveData(viewModelScope.coroutineContext)

    fun getPrefetch(): LiveData<Prefetch> = repository.prefetchData().asLiveData(viewModelScope.coroutineContext)

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