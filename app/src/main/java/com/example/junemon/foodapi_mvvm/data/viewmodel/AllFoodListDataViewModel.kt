package com.example.junemon.foodapi_mvvm.data.viewmodel

import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.repo.AllFoodListDataRepo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AllFoodListDataViewModel(private val repo: AllFoodListDataRepo) : BaseViewModel() {

    fun getAllFoodCategory() {
        compose.add(repo.getAllFoodCategory().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                liveDataState.value = OnComplete(false)
            }.subscribe({
                if (it != null) {
                    liveDataState.value = OnShowCategoryFood(it)
                    liveDataState.value = OnComplete(true)
                }
            }, {
                liveDataState.value = OnError(it.localizedMessage)
            })
        )
    }

    fun getAllFoodArea() {
        compose.add(repo.getAllFoodArea().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                liveDataState.value = OnComplete(false)
            }.subscribe({
                if (it != null) {
                    liveDataState.value = OnShowAreaFood(it)
                    liveDataState.value = OnComplete(true)
                }
            }, {
                liveDataState.value = OnError(it.localizedMessage)
            })
        )
    }

    fun getAllFoodIngredient() {
        compose.add(repo.getAllFoodIngredient().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).doOnSubscribe {
                liveDataState.value = OnComplete(false)
            }.subscribe({
                if (it != null) {
                    liveDataState.value = OnShowIngredientFood(it)
                    liveDataState.value = OnComplete(true)
                }
            }, {
                liveDataState.value = OnError(it.localizedMessage)
            })
        )
    }
}