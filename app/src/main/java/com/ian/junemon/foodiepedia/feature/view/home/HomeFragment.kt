package com.ian.junemon.foodiepedia.feature.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.*
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
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
    lateinit var loadImageHelper: LoadImageHelper

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var homeAdapter: HomeAdapter

    override fun viewCreated() {
        foodVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)
        binding.initView()
        foodVm.getFood()
        observeUiState()
    }

    override fun activityCreated() {
        consumeFilterState()
        observeViewEffect()
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
            navigate(action)

        }
        clicks(rlSearch) {
            val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
            navigate(action)
        }
        with(loadImageHelper) {
            ivNoData.loadWithGlide(getDrawables(R.drawable.no_data))
        }
    }

    private fun consumeFilterState() {
        observe(foodVm.loadSharedPreferenceFilter()) {
            if (it.isEmpty()) {
                binding.tvHomeFilter.text = noFilterValue
            } else {
                binding.tvHomeFilter.text = it
            }
        }
    }

    private fun observeUiState() {
        profileVm.userData.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> with(loadImageHelper) {
                    binding.ivPhotoProfile.loadWithGlide(
                        getDrawables(R.drawable.ic_profiles)
                    )
                }
                it.user != null -> {
                    with(loadImageHelper) {
                        binding.ivPhotoProfile.loadWithGlide(it.user.getPhotoUrl())
                    }
                }
            }
        }

        foodVm.foodCache.asLiveData().observe(viewLifecycleOwner) {

            foodVm.setupLoadingState(it.isLoading)

            when {
                it.data.isNotEmpty() -> {
                    with(binding) {
                        rvHome.visibility = View.VISIBLE
                        ivNoData.visibility = View.GONE
                    }
                    homeAdapter.submitList(it.data.mapToCachePresentation())
                }
                it.errorMessage.isNotEmpty() -> {
                    with(binding) {
                        rvHome.visibility = View.GONE
                        ivNoData.visibility = View.VISIBLE
                    }
                    foodVm.setupSnackbarMessage(it.errorMessage)
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
            data
        )
        navigate(direction)
    }
}