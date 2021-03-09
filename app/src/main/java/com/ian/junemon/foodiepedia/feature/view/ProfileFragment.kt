package com.ian.junemon.foodiepedia.feature.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileFragment : BaseFragmentViewBinding<FragmentProfileBinding>() {
    private val REQUEST_SIGN_IN_PERMISSIONS = 15

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
        consumeProfileData()
        obvserveNavigation()
    }

    private fun FragmentProfileBinding.initView() {
        clicks(fabUpload) {
            val action = ProfileFragmentDirections.actionProfileFragmentToUploadFoodFragment()
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
            startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
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

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
}