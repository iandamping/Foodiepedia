package com.ian.junemon.foodiepedia.feature.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragment
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.model.presentation.FoodCachePresentation
import com.ian.junemon.foodiepedia.core.domain.model.EventObserver
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.feature.vm.SharedDialogListenerViewModel
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.Results
import com.ian.junemon.foodiepedia.core.util.mapToCachePresentation
import kotlinx.android.synthetic.main.item_custom_home.view.*
import kotlinx.android.synthetic.main.item_home.view.ivFoodImage
import timber.log.Timber
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

    private val sharedViewModel: SharedDialogListenerViewModel by activityViewModels()

    private var filterState: String = ""

    private val binding get() = _binding!!

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        foodVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)

        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.initView()
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

        sharedViewModel.setFilterState.observe(viewLifecycleOwner, EventObserver {
            if (it.isNotEmpty()) {
                foodVm.setSharedPreferenceFilter(it)
            }
        })
    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        consumeFoodPrefetch()
        observeFilter()
        filterFood()
        consumeProfileData()
        setupNavigation()
    }

    private fun FragmentHomeBinding.initView() {
        ivFilter.setOnClickListener {
            val bottomFilterDirections =
                HomeFragmentDirections.actionHomeFragmentToBottomFilterFragment()
            navigate(bottomFilterDirections)
        }

        ivPhotoProfile.setOnClickListener {
            foodVm.moveToProfileFragment()
        }
        rlSearch.setOnClickListener {
            foodVm.moveToSearchFragmentEvent()
        }
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
        with(recyclerHelper) {
            recyclerviewCatching {
                checkNotNull(data)
                rvHome.setUpSkidAdapter(items = data,
                    diffUtil = foodPresentationRvCallback,
                    layoutResId = R.layout.item_custom_home,
                    bindHolder = {
                        with(this) {
                            with(loadImageHelper) { ivFoodImage.loadWithGlide(it?.foodImage) }
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
        })
    }

    private fun observeFilter() {
        foodVm.loadSharedPreferenceFilter().observe(viewLifecycleOwner) {
            Timber.e("filter result : $it")
            if (it.isNullOrEmpty()) {
                foodVm.setSharedPreferenceFilter(filterValueBreakfast)
                binding.tvHomeFilter.text = filterValueBreakfast
                filterState = filterValueBreakfast
            } else {
                binding.tvHomeFilter.text = it
                filterState = it
            }
        }
    }

    private fun filterFood() {
        foodVm.getFoodBasedOnFilter().observe(viewLifecycleOwner) { value ->
            when (value) {
                is Results.Error -> {
                    Timber.e(value.exception)
                }
                is Results.Success -> {
                    val userPickData = value.data.filter { it.foodCategory == filterState }
                    binding.setupRecyclerView(userPickData.mapToCachePresentation())
                }
            }
        }
    }

    private fun consumeFoodPrefetch() {
        foodVm.getCache().observe(viewLifecycleOwner, { value ->
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
        })
    }
}