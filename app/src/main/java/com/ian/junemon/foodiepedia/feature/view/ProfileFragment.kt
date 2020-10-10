package com.ian.junemon.foodiepedia.feature.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.requestSignIn
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
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
                    if (it.data.getPhotoUrl()!=null){
                        binding.llProfileData.visibility = View.VISIBLE
                        loadImageHelper.run {
                            binding.ivPhotoProfile.loadWithGlide(it.data.getPhotoUrl())
                        }
                        binding.tvProfileName.text = it.data.getDisplayName()
                        binding.rlSignOut.visibility = View.VISIBLE
                        binding.rlSignIn.visibility = View.GONE
                    }else{
                        binding.rlSignOut.visibility = View.GONE
                        binding.rlSignIn.visibility = View.VISIBLE
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
        findNavController().navigate(action)
    }

    private fun fireSignIn() {
        lifecycleScope.launch {
            val signInIntent = profileVm.initSignIn()
            startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
        }
    }

    private fun fireSignOut() {
        lifecycleScope.launch {
            profileVm.initLogout()
        }
    }
}