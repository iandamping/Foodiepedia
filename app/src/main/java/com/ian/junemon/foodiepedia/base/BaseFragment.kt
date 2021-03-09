package com.ian.junemon.foodiepedia.base

import android.content.Context
import android.content.Intent
import android.os.StrictMode
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.feature.view.upload.UploadFoodFragmentDirections
import dagger.android.support.DaggerFragment
import timber.log.Timber

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
abstract class BaseFragment : DaggerFragment() {




    override fun onAttach(context: Context) {
        super.onAttach(context)
        // dont use this, but i had to
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    /**for real case*/
    protected fun baseNavigate(destination: NavDirections) =
        with(findNavController()) {
            currentDestination?.getAction(destination.actionId)
                ?.let { navigate(destination) }
        }

    /**for integration test*/
//    protected fun baseNavigate(destination: NavDirections) =
//        with(findNavController()) {
//            navigate(destination)
//        }




    protected fun sharedImageIntent(intent: Intent) {
        startActivity(
            Intent.createChooser(
                intent,
                "Share Image"
            )
        )
    }


    protected fun ilegallArgumenCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
        }
    }

    protected fun ilegallStateCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: IllegalStateException) {
            Timber.e(e)
            // commonHelper.timberLogE(e.message)
        }
    }

    protected fun universalCatching(function: () -> Unit) {
        try {
            function.invoke()
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    protected fun backPressed(action:()->Unit) {
        val backCallback by lazy {
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() { // Handle the back button event
                   action.invoke()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            backCallback
        )
    }
}