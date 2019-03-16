package com.example.junemon.foodapi_mvvm.ui.allfood

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodListDataViewModel
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel

class AllFoodPresenter(private val vmFood: AllFoodViewModel, private val vmListFood: AllFoodListDataViewModel) :
    BasePresenter<AllFoodView>() {

    override fun onCreate() {
        vmFood.getAllFoodData()
        vmListFood.getAllFoodArea()
        vmListFood.getAllFoodCategory()
        vmListFood.getAllFoodIngredient()
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
        vmListFood.liveDataState.observe(getLifeCycleOwner(), Observer {
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