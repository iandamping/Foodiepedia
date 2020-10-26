package com.ian.junemon.foodiepedia.feature.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.core.presentation.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.ProfileResults
import com.junemon.model.Results
import com.junemon.model.data.dto.mapToCachePresentation
import com.junemon.model.domain.FoodCacheDomain
import com.junemon.model.presentation.FoodCachePresentation
import kotlinx.android.synthetic.main.item_custom_home.view.*
import kotlinx.android.synthetic.main.item_home.view.ivFoodImage
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel
    private lateinit var profileVm: ProfileViewModel

    @Inject
    lateinit var recyclerHelper: RecyclerHelper

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var gson: Gson

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        foodVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)
        /**Observe loading state to show loading*/
        foodVm.loadingState.observe(viewLifecycleOwner, { show ->
            if (show) {
                binding.disableShimmer()
            } else {
                binding.enableShimmer()
            }
        })

        /**Show a snackbar whenever the [snackbar] is updated a non-null value*/
        foodVm.snackbar.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }
        })
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
    }

    override fun destroyView() {
        foodVm.unregisterSharedPrefStringListener()
        _binding = null
    }

    override fun activityCreated() {
        foodVm.registerSharedPrefStringListener()
        consumeFoodPrefetch()
        consumeProfileData()
        setupNavigation()
    }

    private fun FragmentHomeBinding.initView() {
        ivFilter.setOnClickListener {
            val bottomFilterDirections =
                HomeFragmentDirections.actionHomeFragmentToBottomFilterFragment()
            navigate(bottomFilterDirections)
        }
        /*fabHome.setOnClickListener {
            foodVm.moveToUploadFragment()
        }*/
        ivPhotoProfile.setOnClickListener {
            foodVm.moveToProfileFragment()
        }
        rlSearch.setOnClickListener {
            foodVm.moveToSearchFragmentEvent()
        }
    }

    private fun iniDataView(data: List<FoodCacheDomain>) {
        foodVm.loadSharedPreferenceFilter().observe(viewLifecycleOwner, { localeStatus ->
            if (localeStatus != null && localeStatus != "") {
                binding.tvHomeFilter.text = localeStatus
                val userPickData = data.filter { it.foodCategory == localeStatus }
                binding.setupRecyclerView(userPickData.mapToCachePresentation())
            } else {
                binding.tvHomeFilter.text = filterValueBreakfast
                val defaultBreakfastData = data.filter { it.foodCategory == filterValueBreakfast }
                binding.setupRecyclerView(defaultBreakfastData.mapToCachePresentation())
            }
        })

        // foodVm.eventDissmissFromFilter.observe(viewLifecycleOwner, EventObserver {
        //     requireActivity().run {
        //         val intent by lazy { Intent(this, this::class.java) }
        //         startActivity(intent)
        //         finish()
        //     }
        // })
    }

    private fun FragmentHomeBinding.enableShimmer() {
        if (!shimmerSlider.isShimmerStarted && !shimmerSlider.isShimmerVisible) {
            shimmerSlider.startShimmer()
        }
    }

    private fun FragmentHomeBinding.disableShimmer() {
        if (shimmerSlider.isShimmerStarted && shimmerSlider.isShimmerVisible) {
            shimmerSlider.stopShimmer()
            shimmerSlider.hideShimmer()
            shimmerSlider.visibility = View.GONE
        }
    }

    private fun FragmentHomeBinding.setupRecyclerView(data: List<FoodCachePresentation>?) {
        rvHome.onFlingListener = null
        recyclerHelper.run {
            recyclerviewCatching {
                checkNotNull(data)
                rvHome.setUpSkidAdapter(items = data,
                    diffUtil = foodPresentationRvCallback,
                    layoutResId = R.layout.item_custom_home,
                    bindHolder = {
                        with(this) {
                            loadImageHelper.run { ivFoodImage.loadWithGlide(it?.foodImage) }
                            tv_title.text = it?.foodName
                            tv_description.text = it?.foodDescription
                        }
                    },
                    itemClick = {
                        foodVm.moveToDetailFragment(gson.toJson(this))
                    })
            }
        }
    }

    private fun setupNavigation() {
        foodVm.moveToDetailFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToDetailFoodFragment(it)
        })

        foodVm.moveToProfileFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToProfileFragment()
        })


        foodVm.moveToSearchFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToSearchFragment()
        })
    }

    private fun navigateToDetailFoodFragment(foodValue: String) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(foodValue)
        navigate(action)
    }

    private fun navigateToProfileFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        navigate(action)
    }

    private fun consumeProfileData() {
        profileVm.getUserProfile().observe(viewLifecycleOwner, {
            when (it) {
                is ProfileResults.Success -> {
                    loadImageHelper.run {
                        binding.ivPhotoProfile.loadWithGlide(it.data.getPhotoUrl())
                    }
                }
                else -> {
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

    private fun consumeFoodPrefetch() {
        foodVm.foodPrefetch().observe(viewLifecycleOwner, {
            when (it) {
                is Results.Loading -> {
                    if (!it.cache.isNullOrEmpty()) {
                        foodVm.setupLoadingState(true)
                        iniDataView(it.cache!!)
                    } else {
                        foodVm.setupLoadingState(false)
                    }
                }
                is Results.Success -> {
                    foodVm.setupLoadingState(true)
                    iniDataView(it.data)
                }
                is Results.Error -> {
                    foodVm.setupLoadingState(true)
                    foodVm.setupSnackbarMessage(it.exception.message)
                }
            }
        })
    }
}