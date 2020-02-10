package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.Results
import com.junemon.model.data.dto.mapToCachePresentation
import com.junemon.model.presentation.FoodCachePresentation
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
    lateinit var viewHlper: ViewHelper

    private val gson by lazy { Gson() }
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        consumeProfileData()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            initView()
        }
        setupNavigation()

    }

    private fun FragmentHomeBinding.initView() {
        apply {
            foodVm.getCache().observe(viewLifecycleOwner, Observer { result ->
                when (result) {
                    is Results.Loading -> {
                    }
                    is Results.Error -> {
                        setupRecyclerView(result.cache?.mapToCachePresentation())
                    }
                    is Results.Success -> {
                        setupRecyclerView(result.data.mapToCachePresentation())
                    }
                }
            })
            fabHome.setOnClickListener { foodVm.moveToUploadFragment() }
            ivPhotoProfile.setOnClickListener { foodVm.moveToProfileFragment() }
        }
    }

    private fun FragmentHomeBinding.setupRecyclerView(data: List<FoodCachePresentation>?) {
        apply {
            recyclerHelper.run {
                recyclerviewCatching {
                    checkNotNull(data)
                    rvHome.setUpVerticalGridAdapter(items = data,
                        diffUtil = foodPresentationRvCallback,
                        layoutResId = R.layout.item_home,
                        gridSize = 2,
                        bindHolder = {
                            with(this) {
                                loadImageHelper.run { ivFoodImage.loadWithGlide(it?.foodImage) }
                                tvFoodContributor.text = it?.foodContributor
                            }
                        },
                        itemClick = {
                            foodVm.moveToDetailFragment(gson.toJson(this))
                        })
                }
            }
        }
    }

    private fun setupNavigation() {
        foodVm.moveToDetailFragmentEvent.observe(this, EventObserver {
            navigateToDetailFoodFragment(it)
        })

        foodVm.moveToProfileFragmentEvent.observe(this, EventObserver {
            navigateToProfileFragment()
        })

        foodVm.moveToUploadFragmentEvent.observe(this, EventObserver {
            navigateToUploadFoodFragment()
        })
    }

    private fun navigateToDetailFoodFragment(foodValue: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(foodValue)
        findNavController().navigate(action)
    }

    private fun navigateToUploadFoodFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToUploadFragment()
        findNavController().navigate(action)
    }

    private fun navigateToProfileFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        findNavController().navigate(action)
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
            } else {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_person_gray_24dp
                        )!!
                    )
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