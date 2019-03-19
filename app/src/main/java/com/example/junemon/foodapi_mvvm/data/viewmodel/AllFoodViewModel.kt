package com.example.junemon.foodapi_mvvm.data.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.junemon.foodapi_mvvm.base.BaseViewModel
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnError
import com.example.junemon.foodapi_mvvm.base.OnShowAllFood
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AllFoodViewModel(private val repo: AllFoodRepo) : BaseViewModel() {

    fun getAllFoodData() {
        compose.add(repo.getCategoryFood().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                liveDataState.value = OnComplete(false)
            }.subscribe({
                if (it != null) {
                    liveDataState.value = OnShowAllFood(it)
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