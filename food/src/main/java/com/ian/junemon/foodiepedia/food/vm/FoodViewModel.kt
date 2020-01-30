package com.ian.junemon.foodiepedia.food.vm

import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.ian.junemon.foodiepedia.core.domain.usecase.FoodUseCase
import com.junemon.model.FirebaseResult
import com.junemon.model.Results
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.domain.FoodRemoteDomain
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModel @Inject constructor(private val repository:FoodUseCase):ViewModel() {
    private val _foodData:MutableLiveData<FoodRemoteDomain> = MutableLiveData()
    val foodData:LiveData<FoodRemoteDomain>
        get() = _foodData

    private val _foodImageUri:MutableLiveData<Uri> = MutableLiveData()
    val foodImageUri:LiveData<Uri>
        get() = _foodImageUri

    val etFoodName: MutableLiveData<String> = MutableLiveData()
    val etFoodArea: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient1: MutableLiveData<String> = MutableLiveData()
    val etFoodInstruction: MutableLiveData<String> = MutableLiveData()

    fun <T> observingEditText(
        lifecycleOwner: LifecycleOwner,
        liveData: LiveData<T>,
        data: (T) -> Unit
    ) {
        liveData.observe(lifecycleOwner, Observer {
            data.invoke(it)
        })
    }

    fun setFood(data:FoodRemoteDomain){
        _foodData.value = data
    }

    fun setFoodUri(uri:Uri){
        _foodImageUri.value = uri
    }
    fun getCategorizeCache(category:String):LiveData<List<FoodCacheDomain>> = repository.getCategorizeCache(category)

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repository.getCache()

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): LiveData<FirebaseResult<Nothing>> = repository.uploadFirebaseData(data, imageUri)
}