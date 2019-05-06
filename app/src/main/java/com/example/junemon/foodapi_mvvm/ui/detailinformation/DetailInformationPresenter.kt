package com.example.junemon.foodapi_mvvm.ui.detailinformation

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DetailInformationPresenter(private val vm: AllFoodListDataViewModel) : BasePresenter<DetailInformationView>() {

    override fun onCreate() {
        vm.getAllData()
        initData()
        view()?.initView()
    }


    private fun initData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnComplete -> {
                    setDialogShow(it.show)
                }
                is OnShowAreaFood -> {
                    view()?.getAreaData(it.data)
                }
                is OnShowCategoryFood -> {
                    view()?.getCategoryData(it.data)
                }
                is OnShowIngredientFood -> {
                    view()?.getIngredientData(it.data)
                }
            }
        })
    }
}