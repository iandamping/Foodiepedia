package com.example.junemon.foodapi_mvvm.base

import android.app.ProgressDialog
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import com.example.junemon.foodapi_mvvm.util.Constant

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver, BasePresenterHelper {
    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null
    private lateinit var lifecycleOwner: FragmentActivity
    private lateinit var dialog: ProgressDialog


    fun attachView(view: View, lifeCycleOwner: FragmentActivity) {
        this.view = view
        this.viewLifecycle = lifeCycleOwner.lifecycle
        this.lifecycleOwner = lifeCycleOwner
        setBaseDialog(lifecycleOwner)
        viewLifecycle?.addObserver(this)
    }

    protected fun view(): View? {
        return view
    }

    protected fun getLifeCycleOwner(): LifecycleOwner {
        return lifecycleOwner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
    }

    private fun setBaseDialog(ctx: Context) {
        dialog = ProgressDialog(ctx)
        dialog.setTitle(Constant.dialogTittle)
        dialog.setMessage(Constant.dialogMessage)
        dialog.isIndeterminate = true
    }

    protected fun setDialogShow(status: Boolean) {
        if (dialog != null) {
            if (status) {
                dialog.dismiss()
            } else {
                dialog.show()
            }
        }
    }
}