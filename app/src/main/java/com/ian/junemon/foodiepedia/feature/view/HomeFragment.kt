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
import com.ian.junemon.foodiepedia.core.cache.util.PreferenceHelper
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
import com.ian.junemon.foodiepedia.ui.MainActivity
import com.ian.junemon.foodiepedia.util.Constant.filterKey
import com.ian.junemon.foodiepedia.util.Constant.filterValueBreakfast
import com.junemon.model.WorkerResult
import com.junemon.model.data.dto.mapToCachePresentation
import com.junemon.model.presentation.FoodCachePresentation
import kotlinx.android.synthetic.main.fragment_home.*
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
    lateinit var viewHlper: ViewHelper
    @Inject
    lateinit var prefHelper: PreferenceHelper

    private val bottomFilter by lazy { BottomFilterFragment(this) }
    private val gson by lazy { Gson() }
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
        val localeStatus by lazy { prefHelper.getStringInSharedPreference(filterKey) }
        if (localeStatus == "") {
            binding.tvHomeFilter.text = filterValueBreakfast
            foodVm.getCategorizeCache(filterValueBreakfast)
                .observe(this@HomeFragment.viewLifecycleOwner,
                    Observer { result ->
                        binding.setupRecyclerView(result?.mapToCachePresentation())
                    })
        } else {
            binding.tvHomeFilter.text = localeStatus
            foodVm.getCategorizeCache(localeStatus!!)
                .observe(this@HomeFragment.viewLifecycleOwner,
                    Observer { result ->
                        binding.setupRecyclerView(result?.mapToCachePresentation())
                    })
        }

        foodVm.eventDissmissFromInput.observe(viewLifecycleOwner,EventObserver{
            (activity as MainActivity).run {
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
        profileVm.getUser().observe(viewLifecycleOwner, Observer {
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
        lifecycleScope.launchWhenStarted {
            binding.enableShimmer()
            foodVm.foodPrefetch().collect {
                when (it) {
                    is WorkerResult.SuccessWork -> {
                        binding.disableShimmer()
                        iniDataView()
                    }
                    is WorkerResult.ErrorWork -> {
                        binding.disableShimmer()
                        Snackbar.make(binding.root, it.exception.message!!, Snackbar.LENGTH_SHORT)
                            .show()
                    }
                    is WorkerResult.EmptyData -> {
                        binding.disableShimmer()
                        Snackbar.make(binding.root, "data is empty", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDissmis() {
        foodVm.eventDissmissFromInput()
    }
}