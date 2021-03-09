package com.ian.junemon.foodiepedia.feature.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import com.ian.junemon.foodiepedia.util.shimmerHandler
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.view.HomeFragmentDirections
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.horizontalRecyclerviewInitializer
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.RecyclerHelper
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragmentViewBinding<FragmentHomeBinding>(),
    HomeAdapter.HomeAdapterListener {
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

    private lateinit var homeAdapter: HomeAdapter

    private var filterState: String = ""

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)
        homeAdapter = HomeAdapter(this,loadImageHelper)
        binding.initView()
    }

    override fun activityCreated() {
        consumeFoodPrefetch()
        consumeFilterState()
        consumeFilterFood()
        consumeProfileData()
        obvserveNavigation()
        observeViewEffect()
    }

    private fun obvserveNavigation() {
        observeEvent(foodVm.navigateEvent) {
            navigate(it)
        }
    }

    private fun FragmentHomeBinding.initView() {
        rvHome.apply {
            onFlingListener = null
            horizontalRecyclerviewInitializer()
            adapter = homeAdapter
        }

        clicks(ivFilter) {
            val bottomFilterDirections =
                HomeFragmentDirections.actionHomeFragmentToBottomFilterFragment()
            navigate(bottomFilterDirections)
        }

        clicks(ivPhotoProfile) {
            val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
            foodVm.setNavigate(action)

        }
        clicks(rlSearch) {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            foodVm.setNavigate(action)
        }
    }


    private fun consumeProfileData() {
        observe(profileVm.getUserProfile()) {
            when (it) {
                is ProfileResults.Success -> {
                    with(loadImageHelper) {
                        binding.ivPhotoProfile.loadWithGlide(it.data.getPhotoUrl())
                    }
                }
                else -> {
                    with(loadImageHelper) {
                        binding.ivPhotoProfile.loadWithGlide(
                            ContextCompat.getDrawable(
                                requireContext(),
                                R.drawable.ic_profiles
                            )!!
                        )
                    }
                }

            }
        }
    }

    private fun consumeFilterState() {
        observe(foodVm.loadSharedPreferenceFilter()) {
            if (it.isEmpty()) {
                foodVm.setSharedPreferenceFilter(filterValueBreakfast)
                binding.tvHomeFilter.text = filterValueBreakfast
                filterState = filterValueBreakfast
            } else {
                binding.tvHomeFilter.text = it
                filterState = it
            }
        }
    }

    private fun consumeFilterFood() {
        observe(foodVm.getFoodBasedOnFilter()) { value ->
            when (value) {
                is Results.Error -> {
                    foodVm.setupSnackbarMessage(value.exception.message)
                }
                is Results.Success -> {
                    val userPickData = value.data.filter { it.foodCategory == filterState }
                    with(homeAdapter) {
                        submitList(userPickData.mapToCachePresentation())
                        // Force a redraw
                        this.notifyDataSetChanged()
                    }
                    // binding.setupRecyclerView(userPickData.mapToCachePresentation())
                }
            }
        }
    }

    private fun consumeFoodPrefetch() {
        observe(foodVm.getCache()) { value ->
            when (value) {
                is Results.Loading -> {
                    foodVm.setupLoadingState(false)
                }
                is Results.Success -> {
                    foodVm.setupLoadingState(true)
                }
                is Results.Error -> {
                    foodVm.setupLoadingState(true)
                    foodVm.setupSnackbarMessage(value.exception.message)
                }
            }
        }
    }

    private fun observeViewEffect() {
        /**Observe loading state to show loading*/
        observe(foodVm.loadingState) { show ->
            show.shimmerHandler(binding.shimmerSlider)
        }

        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }

        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    override fun onClicked(data: FoodCachePresentation) {
        val direction = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
            gson.toJson(data)
        )
        foodVm.setNavigate(direction)
    }
}