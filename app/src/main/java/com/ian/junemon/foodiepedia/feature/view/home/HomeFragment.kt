package com.ian.junemon.foodiepedia.feature.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.model.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.util.*
import com.ian.junemon.foodiepedia.util.FoodConstant.FILTER_BUNDLE_KEY
import com.ian.junemon.foodiepedia.util.FoodConstant.FRAGMENT_RESULT_KEY
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class HomeFragment @Inject constructor(
    private val factory: HomeAdapter.Factory,
    private val loadImageHelper: LoadImageHelper,
) : BaseFragmentViewBinding<FragmentHomeBinding>(),
    HomeAdapter.HomeAdapterListener {

    private val homeAdapter: HomeAdapter by lazy {
        factory.create(this)
    }

    private val foodVm: FoodViewModel by viewModels()
    private val profileVm: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(FRAGMENT_RESULT_KEY) { requestKey, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            val result = bundle.getString(FILTER_BUNDLE_KEY)
            // Do something with the result
            if (result != null) {
                foodVm.setSharedPreferenceFilter(result)
            }
        }

    }


    override fun viewCreated() {
        binding.initView()
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
        loadImageHelper.loadWithGlide(ivNoData, getDrawables(R.drawable.no_data))

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
                it.errorMessage.isNotEmpty() -> loadImageHelper.loadWithGlide(
                    binding.ivPhotoProfile,
                    getDrawables(R.drawable.ic_profiles)
                )

                it.user != null -> loadImageHelper.loadWithGlide(
                    binding.ivPhotoProfile,
                    it.user.getPhotoUrl()
                )

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