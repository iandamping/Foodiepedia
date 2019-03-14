package com.example.junemon.foodapi_mvvm.base

import android.app.ProgressDialog
import android.content.Context
import com.example.junemon.foodapi_mvvm.util.Constant.dialogMessage
import com.example.junemon.foodapi_mvvm.util.Constant.dialogTittle

/*
Created by ian 10/March/2019
 */
abstract class BasePresenter : BasePresenterHelper {

    private lateinit var dialog: ProgressDialog

    protected fun setBaseDialog(ctx: Context) {
        dialog = ProgressDialog(ctx)
        dialog.setTitle(dialogTittle)
        dialog.setMessage(dialogMessage)
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