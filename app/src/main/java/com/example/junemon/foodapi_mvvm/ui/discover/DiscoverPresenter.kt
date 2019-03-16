package com.example.junemon.foodapi_mvvm.ui.discover

import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowCategoryFoodDetail
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodCategoryViewModel

class DiscoverPresenter(private val vm: AllFoodCategoryViewModel) : BasePresenter<DiscoverView>() {

    override fun onCreate() {
        view()?.initView()
        vm.getAllFoodCategoryDetail()
        funInitData()
    }

    fun funInitData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> view()?.onShowDefaultFoodCategory(it.data)
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }
}