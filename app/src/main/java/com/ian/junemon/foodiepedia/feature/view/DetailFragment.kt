package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentDetailBinding
import com.junemon.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragment() {
    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var intentHelper: IntentUtilHelper

    private val gson by lazy { Gson() }
    private val passedData by lazy {
        gson.fromJson(
            DetailFragmentArgs.fromBundle(arguments!!).detailValue,
            FoodCachePresentation::class.java
        )
    }

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
        }
        return binding.root
    }

    private fun FragmentDetailBinding.initView() {
        apply {
            ilegallStateCatching {
                checkNotNull(passedData)
                loadImageHelper.run {
                    ivFoodDetail.loadWithGlide(passedData.foodImage)
                }
            }
            btnBack.setOnClickListener {
                it.findNavController().navigateUp()
            }
            btnShare.setOnClickListener {
                intentHelper.run {
                    this@DetailFragment.intentShareImageAndText(
                        scope = lifecycleScope,
                        tittle = passedData.foodName,
                        imageUrl = passedData.foodImage,
                        message = passedData.foodCategory
                    )
                }
            }
        }
    }
}