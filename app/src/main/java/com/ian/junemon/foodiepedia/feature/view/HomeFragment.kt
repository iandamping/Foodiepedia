package com.ian.junemon.foodiepedia.feature.view

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
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.RecyclerHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentHomeBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.CanceledListener
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.util.FoodConstant.foodPresentationRvCallback
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.WorkerResult
import com.junemon.model.data.dto.mapToCachePresentation
import com.junemon.model.presentation.FoodCachePresentation
import kotlinx.android.synthetic.main.item_custom_home.view.*
import kotlinx.android.synthetic.main.item_home.view.ivFoodImage
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class HomeFragment : BaseFragment(), CanceledListener {
    @Inject
    lateinit var foodVm: FoodViewModel
    @Inject
    lateinit var profileVm: ProfileViewModel
    @Inject
    lateinit var recyclerHelper: RecyclerHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var gson :Gson

    private val bottomFilter by lazy { BottomFilterFragment(this) }

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        setBaseDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        /**Observe loading state to show loading*/
        foodVm.loadingState.observe(viewLifecycleOwner, Observer { show ->
            if (show) {
                binding.disableShimmer()
            }else{
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        consumeFoodPrefetch()
        consumeProfileData()
        setupNavigation()
    }


    private fun FragmentHomeBinding.initView() {
        ivFilter.setOnClickListener {
            bottomFilter.show(
                childFragmentManager,
                bottomFilter.tag
            )
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

    private fun iniDataView() {
        val localeStatus by lazy { foodVm.loadSharedPreferenceFilter() }
        if (localeStatus == "") {
            binding.tvHomeFilter.text = filterValueBreakfast
            foodVm.getCategorizeCache(filterValueBreakfast)
                .observe(viewLifecycleOwner,
                    Observer { result ->
                        binding.setupRecyclerView(result?.mapToCachePresentation())
                    })
        } else {
            binding.tvHomeFilter.text = localeStatus
            foodVm.getCategorizeCache(localeStatus)
                .observe(viewLifecycleOwner,
                    Observer { result ->
                        binding.setupRecyclerView(result?.mapToCachePresentation())
                    })
        }

        foodVm.eventDissmissFromInput.observe(viewLifecycleOwner,EventObserver{
            requireActivity().run {
                val intent by lazy { Intent(this,this::class.java) }
                startActivity(intent)
                finish()
            }
        })
    }

    private fun FragmentHomeBinding.enableShimmer(){
        if (!shimmerSlider.isShimmerStarted && !shimmerSlider.isShimmerVisible) {
            shimmerSlider.startShimmer()
        }
    }
    private fun FragmentHomeBinding.disableShimmer(){
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
        findNavController().navigate(action)
    }

    private fun navigateToProfileFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToProfileFragment()
        findNavController().navigate(action)
    }

    private fun navigateToSearchFragment() {
        val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        findNavController().navigate(action)
    }

    private fun consumeProfileData() {
        profileVm.getCacheUserProfile().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(it.photoUser)
                }

            } else {
                loadImageHelper.run {
                    binding.ivPhotoProfile.loadWithGlide(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_profiles
                        )!!
                    )
                }
            }
        })
    }

    private fun consumeFoodPrefetch() {
        foodVm.foodPrefetch().observe(viewLifecycleOwner, Observer {
            when (it) {
                is WorkerResult.Loading ->{
                    foodVm.setupLoadingState(false)
                }
                is WorkerResult.SuccessWork -> {
                    foodVm.setupLoadingState(true)
                    iniDataView()
                }
                is WorkerResult.ErrorWork -> {
                    foodVm.setupLoadingState(true)
                    foodVm.setupSnackbarMessage(it.exception.message)
                }
                is WorkerResult.EmptyData -> {
                    foodVm.setupLoadingState(true)
                    foodVm.setupSnackbarMessage("data is empty")
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDissmis() {
        foodVm.eventDissmissFromInput()
    }
}