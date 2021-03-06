package com.ian.junemon.foodiepedia.ui.fragment.profile

import android.content.Context
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ian.app.helper.util.startActivity
import com.ian.junemon.foodiepedia.FoodApp
import com.ian.junemon.foodiepedia.FoodApp.Companion.mFirebaseAuth
import com.ian.junemon.foodiepedia.FoodApp.Companion.prefHelper
import com.ian.junemon.foodiepedia.base.BaseFragmentPresenter
import com.ian.junemon.foodiepedia.model.UserProfileData
import com.ian.junemon.foodiepedia.ui.activity.MainActivity
import com.ian.junemon.foodiepedia.util.Constant

/**
 *
Created by Ian Damping on 10/06/2019.
Github = https://github.com/iandamping
 */
class ProfilePresenter : BaseFragmentPresenter<ProfileView>() {
    private lateinit var userData: UserProfileData
    private var ctx: Context? = null
    private var listener: FirebaseAuth.AuthStateListener? = null

    override fun onAttach() {
        ctx = getLifeCycleOwner().context
    }

    override fun onCreateView(view: View) {
        view()?.initView(view)
        getFirebaseUserData()

    }

    private fun getFirebaseUserData() {
        listener = FirebaseAuth.AuthStateListener { auth ->
            if (mFirebaseAuth.currentUser != null) {
                if (!FoodApp.prefHelper.getStringInSharedPreference(Constant.saveUserProfile).isNullOrBlank()) {
                    this.userData = FoodApp.gson.fromJson(FoodApp.prefHelper.getStringInSharedPreference(Constant.saveUserProfile), UserProfileData::class.java)
                    view()?.onSuccessGetProfileData(userData)
                } else if (prefHelper.getStringInSharedPreference(Constant.saveUserProfile).isNullOrBlank()) {
                    this.userData = UserProfileData(mFirebaseAuth.currentUser?.uid,
                            mFirebaseAuth.currentUser?.photoUrl.toString(),
                            mFirebaseAuth.currentUser?.displayName,
                            mFirebaseAuth.currentUser?.email)
                    FoodApp.prefHelper.saveStringInSharedPreference(Constant.saveUserProfile, FoodApp.gson.toJson(userData))
                    ctx?.startActivity<MainActivity>()
                }
            } else view()?.onFailedGetData("not logged in")
        }
    }

    fun initOnResume() {
        listener?.let { FoodApp.mFirebaseAuth.addAuthStateListener(it) }
    }

    fun initOnPause() {
        listener?.let { FoodApp.mFirebaseAuth.removeAuthStateListener(it) }
    }

    fun logOut() {
        ctx?.let { AuthUI.getInstance().signOut(it) }
        prefHelper.deleteSharedPreference()
        ctx?.startActivity<MainActivity>()
    }
}