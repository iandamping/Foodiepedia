package com.example.junemon.foodapi_mvvm.ui.detailinformation

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnShowAreaFood
import com.example.junemon.foodapi_mvvm.base.OnShowCategoryFood
import com.example.junemon.foodapi_mvvm.base.OnShowIngredientFood
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel

class DetailInformationPresenter(private val vm: AllFoodListDataViewModel) : BasePresenter<DetailInformationView>() {

    override fun onCreate() {
        vm.getAllFoodIngredient()
        vm.getAllFoodCategory()
        vm.getAllFoodArea()
        initData()
        view()?.initView()
    }


    private fun initData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
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