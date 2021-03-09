package com.ian.junemon.foodiepedia.feature.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueBrunch
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueDinner
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueLunch
import com.ian.junemon.foodiepedia.core.util.DataConstant.filterValueSupper
import com.ian.junemon.foodiepedia.databinding.FragmentBottomFilterBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BottomFilterFragment : BottomSheetDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentBottomFilterBinding? = null
    private val binding get() = _binding!!
    // private val sharedViewModel: SharedDialogListenerViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        _binding = FragmentBottomFilterBinding.inflate(inflater, container, false)
        foodVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initView()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun FragmentBottomFilterBinding.initView() {
        with(loadImageHelper) {
            ivBrunch.loadWithGlide(
                requireContext().resources.getDrawable(
                    R.drawable.ic_filter_4,
                    requireContext().theme
                )
            )

            ivSupper.loadWithGlide(
                requireContext().resources.getDrawable(
                    R.drawable.ic_filter_5,
                    requireContext().theme
                )
            )

            ivLunch.loadWithGlide(
                requireContext().resources.getDrawable(
                    R.drawable.ic_filter_2,
                    requireContext().theme
                )
            )

            ivDinner.loadWithGlide(
                requireContext().resources.getDrawable(
                    R.drawable.ic_filter_3,
                    requireContext().theme
                )
            )

            ivBreakfast.loadWithGlide(
                requireContext().resources.getDrawable(
                    R.drawable.ic_filter_1,
                    requireContext().theme
                )
            )

        }
        clicks(lnPickBreakfast) {
            foodVm.setSharedPreferenceFilter(filterValueBreakfast)
            dismiss()
        }

        clicks(lnPickLunch) {
            foodVm.setSharedPreferenceFilter(filterValueLunch)
            dismiss()
        }

        clicks(lnPickDinner) {
            foodVm.setSharedPreferenceFilter(filterValueDinner)
            dismiss()
        }

        clicks(lnPickBrunch) {
            foodVm.setSharedPreferenceFilter(filterValueBrunch)
            dismiss()
        }

        clicks(lnPickSupper) {
            foodVm.setSharedPreferenceFilter(filterValueSupper)
            dismiss()
        }

        clicks(ivCloseDialog) {
            dismiss()
        }

        observeFilterState(this)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun observeFilterState(view: FragmentBottomFilterBinding) {
        observe(foodVm.loadSharedPreferenceFilter()) {
            when (it) {
                filterValueBreakfast -> {
                    with(loadImageHelper) {
                        view.ivPickBreakfast.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }
                filterValueDinner -> {
                    with(loadImageHelper) {
                        view.ivPickDinner.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }

                filterValueLunch -> {
                    with(loadImageHelper) {
                        view.ivPickLunch.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }

                filterValueBrunch -> {
                    with(loadImageHelper) {
                        view.ivPickBrunch.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }

                filterValueSupper -> {
                    with(loadImageHelper) {
                        view.ivPickSupper.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }

                else -> {
                    with(loadImageHelper) {
                        view.ivPickBreakfast.loadWithGlide(
                            requireContext().resources.getDrawable(
                                R.drawable.ic_check_circle_green_24dp,
                                requireContext().theme
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}