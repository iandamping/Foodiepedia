package com.ian.junemon.foodiepedia.feature.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.FoodConstant.ADMIN_1
import com.ian.junemon.foodiepedia.util.FoodConstant.ADMIN_2
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class ProfileFragment @Inject constructor(private val loadImageHelper: LoadImageHelper) :
    BaseFragmentViewBinding<FragmentProfileBinding>() {

    private lateinit var intentLauncher: ActivityResultLauncher<Intent>

    private val profileVm: ProfileViewModel by viewModels()

    override fun viewCreated() {
        /**Observe loading state to show loading*/
        observe(profileVm.loadingState) { show ->
            setDialogShow(show)
        }
        binding.initView()
        observeUiState()
    }

    override fun activityCreated() {
        initOnBackPressed()
        intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {

        }
    }

    private fun FragmentProfileBinding.initView() {
        clicks(fabUpload) {
            val action = ProfileFragmentDirections.actionProfileFragmentToUploadFoodFragment()
            navigate(action)
        }
        clicks(relativeLayout2) {
            val action = ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment()
            navigate(action)
        }
        clicks(rlSignIn) {
            fireSignIn()
        }
        clicks(rlSignOut) {
            fireSignOut()
        }

        clicks(btnBack) {
            navigateUp()
        }
        loadImageHelper.loadWithGlide(ivFoodProfile, getDrawables(R.drawable.foodiepedia))
    }

    private fun observeUiState() {
        profileVm.userData.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> {
                    with(binding) {
                        rlSignOut.visibility = View.GONE
                        rlSignIn.visibility = View.VISIBLE
                        fabUpload.visibility = View.GONE

                        loadImageHelper.loadWithGlide(
                            ivPhotoProfile,
                            getDrawables(R.drawable.ic_profiles)
                        )
                    }


                }
                it.user != null -> {
                    with(binding) {
                        loadImageHelper.loadWithGlide(
                            ivPhotoProfile,
                            it.user.getPhotoUrl()
                        )
                        llProfileData.visibility = View.VISIBLE
                        tvProfileName.text = it.user.getDisplayName()
                        rlSignOut.visibility = View.VISIBLE
                        rlSignIn.visibility = View.GONE
                        when (it.user.getUid()) {
                            ADMIN_1, ADMIN_2 -> fabUpload.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    private fun fireSignIn() {
        consumeSuspend {
            val signInIntent = profileVm.initSignIn()
            intentLauncher.launch(signInIntent)
            // startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
        }

    }

    private fun fireSignOut() {
        consumeSuspend {
            profileVm.initLogout {
                with(binding) {
                    llProfileData.visibility = View.GONE
                    rlSignOut.visibility = View.GONE
                    rlSignIn.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initOnBackPressed() {
        onBackPressed {
            navigateUp()
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
}