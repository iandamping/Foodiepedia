package com.ian.junemon.foodiepedia.food.view.upload

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.food.databinding.FragmentInstructionBinding
import com.ian.junemon.foodiepedia.food.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.food.vm.FoodViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentInstructionFood : BaseFragment() {
    @Inject
    lateinit var foodVm: FoodViewModel
    private var _binding: FragmentInstructionBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInstructionBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            foodViewModel = foodVm
        }
        consumeViewModelData()
        return binding.root
    }

    private fun consumeViewModelData() {
        foodVm.apply {
            observingEditText<String>(viewLifecycleOwner, etFoodInstruction) {
                Timber.d("ini valuenya $it")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}