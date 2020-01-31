package com.ian.junemon.foodiepedia.foodie.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.food.R
import com.ian.junemon.foodiepedia.food.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.foodie.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.food.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.foodie.vm.FoodViewModel
import com.ian.junemon.foodiepedia.foodie.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.foodie.R
import com.ian.junemon.foodiepedia.foodie.databinding.FragmentHomeBinding
import com.junemon.model.Results
import com.junemon.model.data.dto.mapToCachePresentation
import kotlinx.android.synthetic.main.item_home.view.*
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    @Inject
    lateinit var foodVm: FoodViewModel
    @Inject
    lateinit var profileVm: ProfileViewModel
    @Inject
    lateinit var recyclerHelper: RecyclerHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var viewHlper:ViewHelper

    private val gson by lazy { Gson() }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        // sharedFoodComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
        }
        consumeProfileData()
        return binding.root
    }


    private fun FragmentHomeBinding.initView() {
        apply {
            foodVm.getCache().observe(viewLifecycleOwner, Observer { result ->
                when(result){
                    is Results.Loading ->{

                    }
                    is Results.Error ->{
                            recyclerHelper.run {
                                recyclerviewCatching {
                                    checkNotNull(result.cache)
                                    rvHome.setUpVerticalGridAdapter(items = result.cache?.mapToCachePresentation(),
                                        diffUtil = foodPresentationRvCallback,
                                        layoutResId = R.layout.item_home,
                                        gridSize = 2,
                                        bindHolder = {
                                            with(this){
                                                loadImageHelper.run { ivFoodImage.loadWithGlide(it?.foodImage) }
                                                tvFoodContributor.text = it?.foodContributor
                                            }
                                        },
                                        itemClick = {
                                            binding.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gson.toJson(this)))
                                        })

                                }
                            }
                    }
                    is Results.Success ->{
                        recyclerHelper.run {
                            recyclerviewCatching {
                                rvHome.setUpVerticalGridAdapter(items = result.data.mapToCachePresentation(),
                                    diffUtil = foodPresentationRvCallback,
                                    layoutResId = R.layout.item_home,
                                    gridSize = 2,
                                    bindHolder = {
                                        with(this){
                                            loadImageHelper.run { ivFoodImage.loadWithGlide(it?.foodImage) }
                                            tvFoodContributor.text = it?.foodContributor
                                        }
                                    },
                                    itemClick = {
                                        binding.root.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDetailFragment(gson.toJson(this)))
                                    })

                            }
                        }
                    }
                }
            })
            fabHome.setOnClickListener { it.findNavController().navigate( HomeFragmentDirections.actionHomeFragmentToUploadFragment()) }
            ivPhotoProfile.setOnClickListener { it.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProfileFragment()) }
        }
    }

    private fun consumeProfileData() {
        profileVm.getUser().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(it.photoUser)
                }
                viewHlper.run {
                    binding.fabHome.visible()
                }
            } else{
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(ContextCompat.getDrawable(context!!,R.drawable.ic_person_gray_24dp)!!)
                }
                viewHlper.run {
                    binding.fabHome.gone()
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}