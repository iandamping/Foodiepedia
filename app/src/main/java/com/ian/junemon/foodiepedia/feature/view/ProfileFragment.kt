package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.requestSignIn
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.ProfileResults
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileFragment : BaseFragment() {
    @Inject
    lateinit var profileVm: ProfileViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        setBaseDialog()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initView()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
       /**Observe loading state to show loading*/
        profileVm.loadingState.observe(viewLifecycleOwner, Observer { show ->
            setDialogShow(show)
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            profileVm.initLogout()
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
        profileVm.getUserProfile().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ProfileResults.Loading -> {
                    profileVm.setupLoadingState(false)
                }
                is ProfileResults.Success -> {
                    profileVm.setupLoadingState(true)
                    binding.llProfileData.visibility = View.VISIBLE
                    loadImageHelper.run {
                        binding.ivPhotoProfile.loadWithGlide(it.data.photoUser)
                    }
                    binding.tvProfileName.text = it.data.nameUser
                    binding.rlSignOut.visibility = View.VISIBLE
                    binding.rlSignIn.visibility = View.GONE
                }
                is ProfileResults.Error -> {
                    profileVm.setupLoadingState(true)
                    binding.llProfileData.visibility = View.GONE
                    binding.rlSignIn.visibility = View.VISIBLE
                    binding.rlSignOut.visibility = View.GONE
                }
            }

        })
    }

    private fun fireSignIn() {
        lifecycleScope.launchWhenStarted {
            val signInIntent = profileVm.initSignIn()
            this@ProfileFragment.startActivityForResult(
                signInIntent,
                requestSignIn
            )
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}