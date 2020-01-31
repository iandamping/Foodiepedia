package com.ian.junemon.foodiepedia.foodie.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.food.databinding.FragmentProfileBinding
import com.ian.junemon.foodiepedia.foodie.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.food.util.FoodConstant.requestSignIn
import com.ian.junemon.foodiepedia.foodie.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.foodie.databinding.FragmentProfileBinding
import javax.inject.Inject

/**
 * Created by Ian Damping on 29,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ProfileFragment:BaseFragment() {
    @Inject
    lateinit var profileVm: ProfileViewModel
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        // sharedFoodComponent().inject(this)
        super.onAttach(context)
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
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
        }
        consumeProfileData()
        return binding.root
    }

    private fun FragmentProfileBinding.initView(){
        apply {

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
            } else {
                fireSignIn()
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
}