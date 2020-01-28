package com.ian.junemon.foodiepedia.food.view.upload

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.ian.junemon.foodiepedia.food.databinding.FragmentNameBinding

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentNameFood : Fragment() {

    private var _binding: FragmentNameBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
       activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNameBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}