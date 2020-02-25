package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.cache.util.PreferenceHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentBottomFilterBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.CanceledListener
import com.ian.junemon.foodiepedia.util.Constant.filterKey
import com.ian.junemon.foodiepedia.util.Constant.filterValueBreakfast
import com.ian.junemon.foodiepedia.util.Constant.filterValueBrunch
import com.ian.junemon.foodiepedia.util.Constant.filterValueDinner
import com.ian.junemon.foodiepedia.util.Constant.filterValueLunch
import com.ian.junemon.foodiepedia.util.Constant.filterValueSupper
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BottomFilterFragment(private val canceledState: CanceledListener) :
    BottomSheetDialogFragment() {

    @Inject
    lateinit var prefHelper: PreferenceHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private var _binding: FragmentBottomFilterBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
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
        return binding.root
    }

    private fun FragmentBottomFilterBinding.initView() {
        run {
            val localeStatus by lazy { prefHelper.getStringInSharedPreference(filterKey) }

            loadImageHelper.run {
                ivBrunch.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_filter_4
                    )
                )

                ivSupper.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_filter_5
                    )
                )

                ivLunch.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_filter_2
                    )
                )
                ivDinner.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_filter_3
                    )
                )
                ivBreakfast.setImageDrawable(
                    ContextCompat.getDrawable(
                        context!!,
                        R.drawable.ic_filter_1
                    )
                )
            }
            lnPickBreakfast.setOnClickListener {
                prefHelper.saveStringInSharedPreference(filterKey, filterValueBreakfast)
                dismiss()
            }

            lnPickLunch.setOnClickListener {
                prefHelper.saveStringInSharedPreference(filterKey, filterValueLunch)
                dismiss()
            }

            lnPickDinner.setOnClickListener {
                prefHelper.saveStringInSharedPreference(filterKey, filterValueDinner)
                dismiss()
            }

            lnPickBrunch.setOnClickListener {
                prefHelper.saveStringInSharedPreference(filterKey, filterValueBrunch)
                dismiss()
            }

            lnPickSupper.setOnClickListener {
                prefHelper.saveStringInSharedPreference(filterKey, filterValueSupper)
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
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
                }
                filterValueDinner -> {
                    loadImageHelper.run {
                        ivPickDinner.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
                }

                filterValueLunch -> {
                    loadImageHelper.run {
                        ivPickLunch.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
                }

                filterValueBrunch -> {
                    loadImageHelper.run {
                        ivPickBrunch.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
                }

                filterValueSupper -> {
                    loadImageHelper.run {
                        ivPickSupper.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
                }

                else -> {
                    loadImageHelper.run {
                        ivPickBreakfast.setImageDrawable(
                            ContextCompat.getDrawable(
                                context!!,
                                R.drawable.ic_check_circle_green_24dp
                            )
                        )
                    }
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