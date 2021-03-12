package com.ian.junemon.foodiepedia.feature.view

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import com.ian.junemon.foodiepedia.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.feature.view.upload.UploadFoodFragmentDirections
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.FoodConstant.ADMIN_1
import com.ian.junemon.foodiepedia.util.FoodConstant.ADMIN_2
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileFragment : BaseFragmentViewBinding<FragmentProfileBinding>() {
    private lateinit var intentLauncher: ActivityResultLauncher<Intent>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var profileVm: ProfileViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    override fun viewCreated() {
        profileVm = viewModelProvider(viewModelFactory)
        /**Observe loading state to show loading*/
        observe(profileVm.loadingState){ show ->
            setDialogShow(show)
        }
        binding.initView()
    }

    override fun activityCreated() {
        initOnBackPressed()
        consumeProfileData()
        obvserveNavigation()
        intentLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){

        }
    }

    private fun FragmentProfileBinding.initView() {
        clicks(fabUpload) {
            val action = ProfileFragmentDirections.actionProfileFragmentToUploadFoodFragment()
            profileVm.setNavigate(action)
        }
        clicks(relativeLayout2){
            val action = ProfileFragmentDirections.actionProfileFragmentToFavoriteFragment()
            profileVm.setNavigate(action)
        }
        clicks(rlSignIn) {
            fireSignIn()
        }
        clicks(rlSignOut){
            fireSignOut()
        }

        clicks(btnBack){
            navigateUp()
        }
        with(loadImageHelper){
            ivFoodProfile.loadWithGlide(
                getDrawables(R.drawable.foodiepedia)
            )
        }
    }

    private fun consumeProfileData() {
        profileVm.getUserProfile().observe(viewLifecycleOwner, {
            when (it) {
                is ProfileResults.Success -> {
                    if (it.data.getPhotoUrl().isNullOrEmpty() || it.data.getPhotoUrl() == "null") {
                        with(binding) {
                            rlSignOut.visibility = View.GONE
                            rlSignIn.visibility = View.VISIBLE
                            fabUpload.visibility = View.GONE
                        }
                    } else {
                        with(binding) {
                            with(loadImageHelper){
                                ivPhotoProfile.loadWithGlide(it.data.getPhotoUrl())
                            }
                            llProfileData.visibility = View.VISIBLE
                            tvProfileName.text = it.data.getDisplayName()
                            rlSignOut.visibility = View.VISIBLE
                            rlSignIn.visibility = View.GONE
                            when(it.data.getUid()){
                                ADMIN_1, ADMIN_2 -> fabUpload.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                is ProfileResults.Error -> {
                    with(loadImageHelper) {
                        binding.ivPhotoProfile.loadWithGlide(
                            getDrawables( R.drawable.ic_profiles)
                        )
                    }
                }
            }

        })
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

    private fun obvserveNavigation() {
        observeEvent(profileVm.navigateEvent) {
            navigate(it)
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