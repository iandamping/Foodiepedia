package com.example.junemon.foodapi_mvvm.ui.detail

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowDetailFoodData
import com.example.junemon.foodapi_mvvm.data.viewmodel.DetailFoodViewModel


class DetailFoodPresenter(private val vm: DetailFoodViewModel, private val mView: DetailFoodView) : BasePresenter() {
    //    private val type = object : TypeToken<String>() {}.type
    override fun onCreate(lifeCycleOwner: FragmentActivity) {
        setBaseDialog(lifeCycleOwner)
        vm.liveDataState.observe(lifeCycleOwner, Observer {
            when (it) {
                is OnShowDetailFoodData -> mView.showDetailData(it.data[0])
                is OnComplete -> setDialogShow(it.show)
            }
        })
        mView.initView()
    }

    fun setData(passedData: String?) {
        passedData?.let { vm.setDetailFoodData(it) }

    }
}