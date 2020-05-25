package com.ian.junemon.foodiepedia.feature.view

import android.app.Activity
import android.content.Context
import android.content.Intent
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            initView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestSignIn) {
                inflateLogin()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

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
                    ivFoodProfile.context,
                    R.drawable.foodiepedia
                )!!
            )
        }
    }

    private fun inflateLogin() {
        profileVm.inflateLogin().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(it.photoUser)
                }
                binding.tvProfileName.text = it.nameUser
            }
        })
    }

    private fun consumeProfileData() {
        profileVm.getUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(it.photoUser)
                }
                binding.tvProfileName.text = it.nameUser
                binding.rlSignOut.visibility = View.VISIBLE
                binding.rlSignIn.visibility = View.GONE
            } else {
                binding.rlSignIn.visibility = View.VISIBLE
                binding.rlSignOut.visibility = View.GONE
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