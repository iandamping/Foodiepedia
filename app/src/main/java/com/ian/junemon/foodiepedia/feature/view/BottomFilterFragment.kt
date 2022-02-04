package com.ian.junemon.foodiepedia.feature.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBrunch
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueDinner
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueLunch
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueSupper
import com.ian.junemon.foodiepedia.core.util.DataConstant.noFilterValue
import com.ian.junemon.foodiepedia.databinding.FragmentBottomFilterBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.FoodConstant.FILTER_BUNDLE_KEY
import com.ian.junemon.foodiepedia.util.FoodConstant.FRAGMENT_RESULT_KEY
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.getDrawables
import com.ian.junemon.foodiepedia.util.observe
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class BottomFilterFragment @Inject constructor(private val loadImageHelper: LoadImageHelper) :
    BottomSheetDialogFragment() {

    private val foodVm: FoodViewModel by viewModels()


    private var _binding: FragmentBottomFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        _binding = FragmentBottomFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
        observeFilterState(binding)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun FragmentBottomFilterBinding.initView() {
        with(loadImageHelper) {
            loadWithGlide(ivNoFilter, getDrawables(R.drawable.ic_filter_0))
            loadWithGlide(ivBrunch, getDrawables(R.drawable.ic_filter_4))
            loadWithGlide(ivSupper, getDrawables(R.drawable.ic_filter_5))
            loadWithGlide(ivLunch, getDrawables(R.drawable.ic_filter_2))
            loadWithGlide(ivDinner, getDrawables(R.drawable.ic_filter_3))
            loadWithGlide(ivBreakfast, getDrawables(R.drawable.ic_filter_1))
        }

        clicks(lnPickNoFilter) {
            // Use the Kotlin extension in the fragment-ktx artifact
            setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FILTER_BUNDLE_KEY to noFilterValue))
            dismiss()
        }
        clicks(lnPickBreakfast) {
            // Use the Kotlin extension in the fragment-ktx artifact
            setFragmentResult(
                FRAGMENT_RESULT_KEY,
                bundleOf(FILTER_BUNDLE_KEY to filterValueBreakfast)
            )
            dismiss()
        }

        clicks(lnPickLunch) {
            // Use the Kotlin extension in the fragment-ktx artifact
            setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FILTER_BUNDLE_KEY to filterValueLunch))

            dismiss()
        }

        clicks(lnPickDinner) {
            setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FILTER_BUNDLE_KEY to filterValueDinner))
            dismiss()
        }

        clicks(lnPickBrunch) {
            setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FILTER_BUNDLE_KEY to filterValueBrunch))
            dismiss()
        }

        clicks(lnPickSupper) {
            setFragmentResult(FRAGMENT_RESULT_KEY, bundleOf(FILTER_BUNDLE_KEY to filterValueSupper))
            dismiss()
        }

        clicks(ivCloseDialog) {
            dismiss()
        }


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeFilterState(view: FragmentBottomFilterBinding) {
        observe(foodVm.loadSharedPreferenceFilter()) {
            when (it) {
                noFilterValue -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickNoFilter,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )
                }
                filterValueBreakfast -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickBreakfast,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )
                }
                filterValueDinner -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickDinner,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )
                }

                filterValueLunch -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickLunch,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )
                }

                filterValueBrunch -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickBrunch,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )
                }

                filterValueSupper -> {
                    loadImageHelper.loadWithGlide(
                        view.ivPickSupper,
                        getDrawables(R.drawable.ic_check_circle_green_24dp)
                    )

                }

                else -> {
                    foodVm.setSharedPreferenceFilter(noFilterValue)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}