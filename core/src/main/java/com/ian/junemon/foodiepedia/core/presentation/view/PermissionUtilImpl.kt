package com.ian.junemon.foodiepedia.core.presentation.view

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class PermissionUtilImpl @Inject constructor(@ApplicationContext private val context: Context) :
    PermissionHelper {

    override fun requestGranted(permissions: Array<String>): Boolean = permissions.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestingPermissionsResult(
        fragment: Fragment,
        permissionCode: Int,
        requestCode: Int,
        grantResults: IntArray,
        permissionGranted: () -> Unit,
        permissionDenied: () -> Unit
    ) {
        when (requestCode) {
            permissionCode -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    permissionGranted.invoke()
                } else {
                    permissionDenied.invoke()
                }
            }
            else -> permissionDenied.invoke()
        }
    }

    override fun requestingPermission(
        activity: Activity,
        permissions: Array<String>,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }
}