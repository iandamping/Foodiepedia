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
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FoodViewModel @Inject constructor(private val repository:FoodUseCase):ViewModel() {

    val etFoodName: MutableLiveData<String> = MutableLiveData()
    val etFoodArea: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient1: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient2: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient3: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient4: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient5: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient6: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient7: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient8: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient9: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient10: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient11: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient12: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient13: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient14: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient15: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient16: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient17: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient18: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient19: MutableLiveData<String> = MutableLiveData()
    val etFoodIngredient20: MutableLiveData<String> = MutableLiveData()
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

    fun getCache(): LiveData<Results<List<FoodCacheDomain>>> = repository.getCache()

    fun uploadFirebaseData(data: FoodRemoteDomain, imageUri: Uri): Flow<FirebaseResult<Nothing>> = repository.uploadFirebaseData(data, imageUri)
}