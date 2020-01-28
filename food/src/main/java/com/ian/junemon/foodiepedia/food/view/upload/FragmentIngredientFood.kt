package com.ian.junemon.foodiepedia.food.view.upload

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.food.databinding.FragmentIngredientBinding
import com.ian.junemon.foodiepedia.food.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.food.vm.FoodViewModel
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentIngredientFood : BaseFragment() {
    @Inject
    lateinit var foodVm: FoodViewModel
    private var _binding: FragmentIngredientBinding? = null
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
        _binding = FragmentIngredientBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            foodViewModel = foodVm
        }
        consumeViewModelData()
        return binding.root
    }

    private fun consumeViewModelData() {
        foodVm.apply {
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient1) {
                Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient2) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient3) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient4) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient5) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient6) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient7) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient8) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient9) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient10) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient11) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient12) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient13) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient14) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient15) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient16) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient17) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient18) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient19) {
               Timber.d("ini valuenya $it")
            }
            observingEditText<String>(viewLifecycleOwner, etFoodIngredient20) {
               Timber.d("ini valuenya $it")
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}