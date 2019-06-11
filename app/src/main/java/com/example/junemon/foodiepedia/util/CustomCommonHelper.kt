package com.example.junemon.foodiepedia.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.example.junemon.foodiepedia.R
import com.ian.app.helper.util.layoutInflater
import com.ian.app.helper.util.loadWithGlide
import kotlinx.android.synthetic.main.custom_delete_item_dialog.view.*

/**
 *
Created by Ian Damping on 11/06/2019.
Github = https://github.com/iandamping
 */

inline fun Context.alertHelperDelete(imageUrl: String?, crossinline doSomething: () -> Unit) {
    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    val dialogView = inflater.inflate(R.layout.custom_delete_item_dialog, null)
    dialogView.ivAlertDelete.loadWithGlide(imageUrl)
    dialogBuilder.setPositiveButton("Oke") { dialog, _ ->
        dialog.dismiss()
        doSomething()
    }
    dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
        dialog.dismiss()
    }

    dialogBuilder.setView(dialogView)
    val alert = dialogBuilder.create()
    alert.setCancelable(false)
    alert.show()

}