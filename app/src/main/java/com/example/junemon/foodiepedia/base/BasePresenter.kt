package com.example.junemon.foodiepedia.base

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.example.junemon.foodiepedia.R
import com.ian.app.helper.util.layoutInflater

/**
 *
Created by Ian Damping on 06/05/2019.
Github = https://github.com/iandamping
 */

abstract class BasePresenter<View> : ViewModel(), BasePresenterHelper {
    private var view: View? = null
    private lateinit var lifecycleOwner: FragmentActivity
    private lateinit var dialog: AlertDialog
//    private lateinit var userData: UserProfileData
//    private var listener: FirebaseAuth.AuthStateListener? = null


    fun attachView(view: View, lifeCycleOwner: FragmentActivity) {
        this.view = view
        this.lifecycleOwner = lifeCycleOwner
        setBaseDialog(lifecycleOwner)
    }

//    protected fun getFirebaseUserData(loggedIn: (UserProfileData) -> Unit, notLoggedIn: () -> Unit) {
//        listener = FirebaseAuth.AuthStateListener { auth ->
//            if (auth.currentUser != null) {
//                if (!prefHelper.getStringInSharedPreference(saveUserProfile).isNullOrBlank()) {
//                    this.userData = gson.fromJson(prefHelper.getStringInSharedPreference(saveUserProfile), UserProfileData::class.java)
//                    loggedIn(userData)
//                } else {
//                    this.userData = UserProfileData(mFirebaseAuth.currentUser?.uid,
//                            mFirebaseAuth.currentUser?.photoUrl.toString(),
//                            mFirebaseAuth.currentUser?.displayName,
//                            mFirebaseAuth.currentUser?.email)
//                    prefHelper.saveStringInSharedPreference(saveUserProfile, gson.toJson(userData))
//                    loggedIn(userData)
//                }
//            } else notLoggedIn
//        }
//    }

    protected fun view(): View? {
        return view
    }

    protected fun getLifeCycleOwner(): FragmentActivity {
        return lifecycleOwner
    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
//    private fun onResume() {
//        listener?.let { FoodApp.mFirebaseAuth.addAuthStateListener(it) }
//    }
//
//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    private fun onPause() {
//        listener?.let { FoodApp.mFirebaseAuth.removeAuthStateListener(it) }
//    }

    override fun onCleared() {
        super.onCleared()
        view = null
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