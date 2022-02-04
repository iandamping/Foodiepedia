package com.ian.junemon.foodiepedia.core.presentation.view

import android.app.Activity
import androidx.fragment.app.Fragment

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
interface PermissionHelper {

    fun requestGranted(permissions:Array<String>):Boolean

    @SuppressWarnings("deprecated")
    fun onRequestingPermissionsResult(
        fragment: Fragment,
        permissionCode:Int,
        requestCode: Int,
        grantResults: IntArray,
        permissionGranted:() ->Unit = {},
        permissionDenied:()->Unit = {}
    )

    fun requestingPermission(
        activity: Activity,
        permissions:Array<String>,
        requestCode: Int
    )
}