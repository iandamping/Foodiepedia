package com.example.junemon.foodapi_mvvm.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import com.example.junemon.foodapi_mvvm.R
import org.jetbrains.anko.layoutInflater

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

abstract class BasePresenter<View> : ViewModel(), LifecycleObserver, BasePresenterHelper {
    private var view: View? = null
    private var viewLifecycle: Lifecycle? = null
    private lateinit var lifecycleOwner: FragmentActivity
    private lateinit var dialog: AlertDialog


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

    protected fun getLifeCycleOwner(): FragmentActivity {
        return lifecycleOwner
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onViewDestroyed() {
        view = null
        viewLifecycle = null
    }

    private fun setBaseDialog(ctx: Context) {
        val dialogBuilder = AlertDialog.Builder(ctx)
        val inflater = ctx.layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_loading, null)

        dialogBuilder.setView(dialogView)
        dialog = dialogBuilder.create()
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
    }

    protected fun setDialogShow(status: Boolean) {
        if (status) {
            dialog.dismiss()
        } else {
            dialog.show()
        }
    }
}