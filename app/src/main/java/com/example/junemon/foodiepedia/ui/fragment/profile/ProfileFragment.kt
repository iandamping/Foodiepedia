package com.example.junemon.foodiepedia.ui.fragment.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.junemon.foodiepedia.R
import com.example.junemon.foodiepedia.model.UserProfileData
import com.example.junemon.foodiepedia.util.Constant.RequestSignIn
import com.example.junemon.foodiepedia.util.initPresenter
import com.firebase.ui.auth.AuthUI
import com.ian.app.helper.util.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

/**
 *
Created by Ian Damping on 10/06/2019.
Github = https://github.com/iandamping
 */
class ProfileFragment : Fragment(), ProfileView {

    private lateinit var presenter: ProfilePresenter
    private var actualView: View? = null
    private val loginProvider = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
    )

    override fun onAttach(context: Context) {
        super.onAttach(context)
        presenter = initPresenter { ProfilePresenter() }.apply {
            this.attachView(this@ProfileFragment, this@ProfileFragment)
            this.onAttach()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val views = container?.inflates(R.layout.fragment_profile)
        views?.let { presenter.onCreateView(it) }
        return views
    }

    override fun onSuccessGetProfileData(data: UserProfileData) {
        logE(data.nameUser)
        if (data.photoUser != null) {
            actualView?.ivPhotoProfile?.loadWithGlide(data.photoUser)
        }
        if (data.nameUser != null) {
            actualView?.tvProfileName?.text = data.nameUser
        }
        if (data.emailUser != null) {
            actualView?.tvEmailUser?.text = data.emailUser
        }

        if (data.emailUser != null) {
            actualView?.lnProfileEmail?.visible()
            actualView?.tvEmailUser?.text = data.emailUser
        } else {
            actualView?.lnProfileEmail?.gone()
        }
        actualView?.lnAfterLogin?.visible()
        actualView?.btnLogin?.visible()
        actualView?.lnGoogleLogin?.gone()
        actualView?.btnLogin?.text = context?.getString(R.string.logout)
        actualView?.btnLogin?.setOnClickListener {
            presenter.logOut()
        }
    }

    override fun initView(view: View) {
        this.actualView = view
        actualView?.lnGoogleLogin?.setOnClickListener {
            createSignInIntent()
        }
        actualView?.btnProfileEditUser?.setOnClickListener {
            createSignInIntent()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.initOnResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.initOnPause()
    }

    override fun onFailedGetData(msg: String?) {
        actualView?.tvProfileName?.gone()
        actualView?.btnProfileEditUser?.gone()
        actualView?.lnProfileEmail?.gone()
        actualView?.btnLogin?.gone()
        actualView?.lnGoogleLogin?.visible()
    }


    private fun createSignInIntent() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(loginProvider)
                        .build(),
                RequestSignIn
        )
    }
}