package com.example.junemon.foodapi_mvvm.ui.allfood

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowAllFood
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodViewModel

class AllFoodPresenter(private val vm: AllFoodViewModel, private val mView: AllFoodView) : BasePresenter() {

    override fun onCreate(lifeCycleOwner: FragmentActivity) {
        setBaseDialog(lifeCycleOwner)
        vm.liveDataState.observe(lifeCycleOwner, Observer {
            when (it) {
                is OnShowAllFood -> mView.getDetailFood(it.data)
                is OnComplete -> setDialogShow(it.show)
            }
        })
        mView.initView()
    }

    fun setData() {
        vm.getAllFoodData()
    }
}