package com.ian.junemon.foodiepedia.base

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.core.databinding.CustomLoadingBinding
import com.ian.junemon.foodiepedia.util.layoutInflater
import kotlinx.coroutines.Job


/**
 * Created by Ian Damping on 27,February,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragmentDataBinding<out VB : ViewDataBinding> : BaseFragment() {

    private var _binding: ViewDataBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    private var baseJob: Job? = null
    private var _loadingBinding: CustomLoadingBinding? = null
    private val loadingBinding get() = _loadingBinding!!
    private lateinit var alert: AlertDialog


    protected fun navigate(destination: NavDirections) = baseNavigate(destination)

    protected fun navigateUp() =
        with(findNavController()) {
            navigateUp()
        }

    protected fun consumeSuspend(func: suspend () -> Unit) {
        baseJob?.cancel()
        baseJob = lifecycleScope.launchWhenStarted {
            func.invoke()
        }
    }


    abstract fun viewCreated()

    abstract fun activityCreated()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        setBaseDialog(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewCreated()
        activityCreated()

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        baseJob?.cancel()
        _loadingBinding = null
    }

    private fun setBaseDialog(context: Context) {
        _loadingBinding = CustomLoadingBinding.inflate(context.layoutInflater)

        val dialogBuilder = AlertDialog.Builder(context).run {
            setView(loadingBinding.root)
        }

        alert = dialogBuilder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        with(loadingBinding) {
            lottieAnimation.enableMergePathsForKitKatAndAbove(true)
        }
    }

    fun setDialogShow(status: Boolean) {
        if (::alert.isInitialized) {
            if (status) {
                alert.dismiss()
            } else {
                alert.show()
            }
        }

    }

    protected fun onBackPressed(action:()->Unit) = backPressed(action)

}