package com.example.junemon.foodapi_mvvm.ui.allfood

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowAllFood
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class AllFoodPresenter(private val vmFood: AllFoodViewModel) :
    BasePresenter<AllFoodView>() {

    override fun onCreate() {
        vmFood.getAllFoodData()
        initAllData()
        view()?.initView()
    }

    private fun initAllData() {
        vmFood.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowAllFood -> view()?.getDetailFood(it.data)
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }
}