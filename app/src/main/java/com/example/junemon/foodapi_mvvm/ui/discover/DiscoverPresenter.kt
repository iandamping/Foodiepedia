package com.example.junemon.foodapi_mvvm.ui.discover

import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BasePresenter
import com.example.junemon.foodapi_mvvm.base.OnComplete
import com.example.junemon.foodapi_mvvm.base.OnShowCategoryFoodDetail
import com.example.junemon.foodapi_mvvm.data.viewmodel.AllFoodCategoryViewModel

class DiscoverPresenter(private val mView: DiscoverView) : BasePresenter() {
    private lateinit var owner: LifecycleOwner
    private lateinit var ctx: Context
    override fun onCreate(lifeCycleOwner: FragmentActivity) {
        this.owner = lifeCycleOwner
        this.ctx = lifeCycleOwner.applicationContext
        setBaseDialog(lifeCycleOwner)
        mView.initView()
    }

    fun getDefaultData(vm: AllFoodCategoryViewModel) {
        vm.getAllFoodCategoryDetail()
        vm.liveDataState.observe(owner, Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> mView.onShowDefaultFoodCategory(it.data)
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }
}