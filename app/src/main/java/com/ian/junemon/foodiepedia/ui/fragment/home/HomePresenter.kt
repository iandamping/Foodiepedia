package com.ian.junemon.foodiepedia.ui.fragment.home

import android.view.View
import androidx.lifecycle.Observer
import com.ian.junemon.foodiepedia.base.*
import com.ian.junemon.foodiepedia.data.viewmodel.AllFoodViewModel

/**
 *
Created by Ian Damping on 27/05/2019.
Github = https://github.com/iandamping
 */
class HomePresenter(private val allFoodVM: AllFoodViewModel) : BaseFragmentPresenter<HomeView>() {

    override fun onAttach() {
//        getLifeCycleOwner().checkConnectivityStatus {
//            if (it) {
//                allFoodVM.getAllFoodData()
//            } else {
//                getLifeCycleOwner().context?.myToast(Constant.checkYourConnection)
//            }
//        }
        allFoodVM.getAllFoodData()
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)

    }

    fun initAllData() {
        allFoodVM.liveDataState.observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
            when (it) {
                is OnShowCategoryFoodDetail -> {
                    it.data?.observe(getLifeCycleOwner().viewLifecycleOwner, Observer { categoryData ->
                        view()?.onGetAllFoodCategoryDetails(categoryData)
                    })
                }
                is OnShowAllFood -> {
                    it.data?.observe(getLifeCycleOwner().viewLifecycleOwner, Observer { allFoodData ->
                        view()?.onGetAllFood(allFoodData)
                    })
                }
                is OnShowRandomFood -> view()?.onGetRandomFood(it.data?.get(0))
                is OnError -> view()?.onFailGetAllFoodCategoryDetails()
            }
        })
    }


}