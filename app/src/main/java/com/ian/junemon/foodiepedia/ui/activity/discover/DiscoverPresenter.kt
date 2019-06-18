package com.ian.junemon.foodiepedia.ui.activity.discover

import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.BasePresenter
import com.ian.junemon.foodiepedia.base.OnComplete
import com.ian.junemon.foodiepedia.base.OnShowCategoryFoodDetail
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodCategoryViewModel

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

class DiscoverPresenter(private val vm: AllFoodCategoryViewModel) : BasePresenter<DiscoverView>() {

    override fun onCreate() {
//        getLifeCycleOwner().checkConnectivityStatus {
//            if (it) {
//                vm.getAllFoodCategoryDetail()
//            } else {
//                getLifeCycleOwner().myToast(Constant.checkYourConnection)
//            }
//        }
        view()?.initView()
        vm.getAllFoodCategoryDetail()
        funInitData()
    }

    private fun funInitData() {
        vm.liveDataState.observe(getLifeCycleOwner(), Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> {
                    it.data?.observe(getLifeCycleOwner(), Observer { data ->
                        view()?.onShowDefaultFoodCategory(data)
                    })
                }
                is OnComplete -> setDialogShow(it.show)
            }
        })
    }
}