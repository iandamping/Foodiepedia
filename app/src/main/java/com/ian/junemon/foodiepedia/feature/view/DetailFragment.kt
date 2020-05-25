package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.IntentUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentDetailBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.junemon.model.data.dto.mapToDetailDatabasePresentation
import com.junemon.model.presentation.FoodCachePresentation
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class DetailFragment : BaseFragment() {
    @Inject
    lateinit var foodVm: FoodViewModel
    @Inject
    lateinit var loadImageHelper: LoadImageHelper
    @Inject
    lateinit var intentHelper: IntentUtilHelper
    @Inject
    lateinit var gson :Gson

    private var idForDeleteItem: Int? = null
    private var isFavorite: Boolean = false
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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            consumeBookmarkData()
        }
        setupNavigation()
    }

    private fun FragmentDetailBinding.initView() {
            ilegallStateCatching {
                checkNotNull(passedData)
                loadImageHelper.run {
                    ivFoodDetail.loadWithGlide(passedData.foodImage)
                }
            }

            btnBookmark.setOnClickListener {
                if (isFavorite) {
                    if (idForDeleteItem != null) foodVm.deleteSelectedId(idForDeleteItem!!)
                } else {
                    foodVm.setCacheDetailFood(passedData.mapToDetailDatabasePresentation())
                }
            }

            btnBack.setOnClickListener {
                foodVm.moveDetailToHomeFragment()
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
            tvFoodName.text = passedData.foodName
            tvFoodCategory.text = passedData.foodCategory
            tvFoodContributor.text = passedData.foodContributor
            tvFoodArea.text = passedData.foodArea
            tvFoodDescription.text = passedData.foodDescription

            appbarDetailLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, i ->
                var isShow = true
                var scrollRange: Int = -1

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + i == 0) {
                    collapsingToolbar.title = passedData.foodName
                    // tvDetailTittles.visibility = View.GONE
                    // isShow = true
                } else if (isShow) {
                    collapsingToolbar.title = " "
                    // tvDetailTittles.visibility = View.VISIBLE
                    // isShow = false
                }
            })
    }

    private fun FragmentDetailBinding.consumeBookmarkData() {
        foodVm.getSavedDetailCache().observe(viewLifecycleOwner, Observer { result ->
            if (!result.isNullOrEmpty()) {
                val data = result.filter { it.foodName == passedData.foodName }
                if (data.isNotEmpty()) {
                    data.forEach {
                        if (it.foodName == passedData.foodName) {
                            idForDeleteItem = it.localFoodID
                            isFavorite = true
                            bookmarkedState = isFavorite
                        }
                    }
                } else {
                    isFavorite = false
                    bookmarkedState = isFavorite
                }
            } else {
                isFavorite = false
                bookmarkedState = isFavorite
            }
        })
    }

    private fun setupNavigation() {
        foodVm.moveDetailToHomeFragmentEvent.observe(viewLifecycleOwner, EventObserver {
            navigateToHomeFragment()
        })
    }

    private fun navigateToHomeFragment() {
        findNavController().navigateUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}