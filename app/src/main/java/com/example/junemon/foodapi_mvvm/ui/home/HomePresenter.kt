package com.example.junemon.foodapi_mvvm.ui.home

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.*
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
class HomePresenter(private val allFoodVM: AllFoodViewModel) : BasePresenter<HomeView>() {

    override fun onCreate() {
        view()?.initView()
        allFoodVM.getAllFoodData()
        initAllData()
    }

    private fun initAllData() {
        allFoodVM.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> view()?.onGetAllFoodCategoryDetails(it.data)
                is OnShowAllFood -> view()?.onGetAllFood(it.data)
                is OnShowRandomFood -> view()?.onGetRandomFood(it.data?.get(0))
                is OnComplete -> setDialogShow(it.show)
                is OnError -> view()?.onFailGetAllFoodCategoryDetails()
            }
        })
    }


}