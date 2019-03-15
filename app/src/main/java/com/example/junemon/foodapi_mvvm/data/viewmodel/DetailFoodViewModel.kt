package com.example.junemon.foodapi_mvvm.data.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnError
import com.example.junemon.foodapi_mvvm.base.OnShowDetailFoodData
import com.example.junemon.foodapi_mvvm.data.repo.DetailFoodRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class DetailFoodViewModel(private val repo: DetailFoodRepo) : BaseViewModel() {

    fun setDetailFoodData(foodId: String) {
        compose.add(repo.getDetailFood(foodId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                    liveDataState.value = OnComplete(false)
                }.subscribe({
                    if (it != null) {
                        liveDataState.value = OnShowDetailFoodData(it)
                        liveDataState.value = OnComplete(true)
                    }
                }, {
                    liveDataState.value = OnError(it.localizedMessage)
                })
        )
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        dispose()
    }

}