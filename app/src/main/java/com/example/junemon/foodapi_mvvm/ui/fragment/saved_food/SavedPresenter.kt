package com.example.junemon.foodapi_mvvm.ui.fragment.saved_food

import android.view.View
import androidx.lifecycle.Observer
import com.example.junemon.foodapi_mvvm.base.BaseFragmentPresenter
import com.example.junemon.foodapi_mvvm.base.OnGetLocalData
import com.example.junemon.foodapi_mvvm.data.viewmodel.LocalDataViewModel

/**
 *
Created by Ian Damping on 07/06/2019.
Github = https://github.com/iandamping
 */
class SavedPresenter(private val vm: LocalDataViewModel) : BaseFragmentPresenter<SavedView>() {

    override fun onAttach() {
    }

    fun initLocalData() {
        vm.getLocalData().apply {
            vm.liveDataState.observe(getLifeCycleOwner().viewLifecycleOwner, Observer {
                when (it) {
                    is OnGetLocalData -> it.data.observe(getLifeCycleOwner().viewLifecycleOwner, Observer { localData ->
                        if (localData.isEmpty()) {
                            view()?.onFailedGetData("Empty")
                        } else {
                            view()?.onSuccesGetLocalData(localData)
                        }
                    })
                }
            })
        }
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
    }
}