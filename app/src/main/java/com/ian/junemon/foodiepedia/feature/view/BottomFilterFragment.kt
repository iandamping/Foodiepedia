package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueBreakfast
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueBrunch
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueDinner
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueLunch
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.filterValueSupper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentBottomFilterBinding
import com.ian.junemon.foodiepedia.feature.util.CanceledListener
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BottomFilterFragment(private val canceledState: CanceledListener) : BottomSheetDialogFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel

    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentBottomFilterBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        binding.initView()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomFilterBinding.inflate(inflater, container, false)
        foodVm = viewModelProvider(viewModelFactory)
        return binding.root
    }

    private fun FragmentBottomFilterBinding.initView() {
        val localeStatus by lazy { foodVm.loadSharedPreferenceFilter() }

        loadImageHelper.run {
            ivBrunch.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_filter_4
                )
            )

            ivSupper.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_filter_5
                )
            )

            ivLunch.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_filter_2
                )
            )
            ivDinner.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_filter_3
                )
            )
            ivBreakfast.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_filter_1
                )
            )
        }
        lnPickBreakfast.setOnClickListener {
            foodVm.setSharedPreferenceFilter(filterValueBreakfast)
            dismiss()
        }

        lnPickLunch.setOnClickListener {
            foodVm.setSharedPreferenceFilter(filterValueLunch)
            dismiss()
        }

        lnPickDinner.setOnClickListener {
            foodVm.setSharedPreferenceFilter(filterValueDinner)
            dismiss()
        }

        lnPickBrunch.setOnClickListener {
            foodVm.setSharedPreferenceFilter(filterValueBrunch)
            dismiss()
        }

        lnPickSupper.setOnClickListener {
            foodVm.setSharedPreferenceFilter(filterValueSupper)
            dismiss()
        }

        ivCloseDialog.setOnClickListener {
            dismiss()
        }

        when (localeStatus) {
            filterValueBreakfast -> {
                loadImageHelper.run {
                    ivPickBreakfast.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }
            filterValueDinner -> {
                loadImageHelper.run {
                    ivPickDinner.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }

            filterValueLunch -> {
                loadImageHelper.run {
                    ivPickLunch.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }

            filterValueBrunch -> {
                loadImageHelper.run {
                    ivPickBrunch.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }

            filterValueSupper -> {
                loadImageHelper.run {
                    ivPickSupper.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }

            else -> {
                loadImageHelper.run {
                    ivPickBreakfast.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        canceledState.onDissmis()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}