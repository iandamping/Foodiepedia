package com.ian.junemon.foodiepedia.feature.view

import android.content.Context
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
import com.ian.junemon.foodiepedia.feature.di.component.DaggerFoodComponent
import com.ian.junemon.foodiepedia.ui.MainActivity
import com.ian.junemon.foodiepedia.util.Constant.filterKey
import com.ian.junemon.foodiepedia.util.Constant.filterValueBreakfast
import com.ian.junemon.foodiepedia.util.Constant.filterValueDinner
import com.ian.junemon.foodiepedia.util.Constant.filterValueLunch
import javax.inject.Inject

/**
 * Created by Ian Damping on 15,February,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class BottomFilterFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var prefHelper: PreferenceHelper
    @Inject
    lateinit var loadImageHelper: LoadImageHelper

    private val localeStatus by lazy { prefHelper.getStringInSharedPreference(filterKey) }

    private var _binding: FragmentBottomFilterBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        DaggerFoodComponent.factory().create((this.activity as MainActivity).mainActivityComponent)
            .inject(this)
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
        apply {
            loadImageHelper.run {
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
                    ivPickDinner.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }

                filterValueLunch -> {
                    ivPickLunch.setImageDrawable(
                        ContextCompat.getDrawable(
                            context!!,
                            R.drawable.ic_check_circle_green_24dp
                        )
                    )
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}