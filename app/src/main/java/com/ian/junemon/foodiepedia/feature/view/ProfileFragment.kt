package com.ian.junemon.foodiepedia.feature.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.core.presentation.util.EventObserver
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.ProfileResults
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileFragment : BaseFragment() {
    private val REQUEST_SIGN_IN_PERMISSIONS = 15

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var profileVm: ProfileViewModel
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        profileVm = viewModelProvider(viewModelFactory)
        /**Observe loading state to show loading*/
        profileVm.loadingState.observe(viewLifecycleOwner, { show ->
            setDialogShow(show)
        })
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()

    }

    override fun destroyView() {
        _binding = null

    }

    override fun activityCreated() {
        consumeProfileData()
        setupNavigation()
    }


    private fun FragmentProfileBinding.initView() {
        fabUpload.setOnClickListener {
            profileVm.moveToUploadFragment()
        }
        rlSignIn.setOnClickListener {
            fireSignIn()
        }
        rlSignOut.setOnClickListener {
            fireSignOut()
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
        loadImageHelper.run {
            ivFoodProfile.loadWithGlide(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.foodiepedia
                )!!
            )
        }
    }

    private fun consumeProfileData() {
        profileVm.getUserProfile().observe(viewLifecycleOwner, {
            when (it) {
                is ProfileResults.Success -> {
                    if (it.data.getPhotoUrl().isNullOrEmpty() || it.data.getPhotoUrl() == "null"){
                        with(binding){
                            rlSignOut.visibility = View.GONE
                            rlSignIn.visibility = View.VISIBLE
                        }
                    }else{
                        with(binding){
                            loadImageHelper.run {
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
                    loadImageHelper.run {
                        binding.ivPhotoProfile.loadWithGlide(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_profiles
                            )!!
                        )
                    }
                }
            }

        })
    }

    private fun setupNavigation() {
        profileVm.moveToUploadFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToUploadFragment()
        })
    }

    private fun navigateToUploadFragment() {
        val action = ProfileFragmentDirections.actionProfileFragmentToUploadFoodFragment()
        navigate(action)
    }

    private fun fireSignIn() {
        lifecycleScope.launch {
            val signInIntent = profileVm.initSignIn()
            startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
        }
    }

    private fun fireSignOut() {
        lifecycleScope.launch {
            profileVm.initLogout{
                with(binding){
                    llProfileData.visibility = View.GONE
                    rlSignOut.visibility = View.GONE
                    rlSignIn.visibility = View.VISIBLE
                }
            }
        }
    }
}