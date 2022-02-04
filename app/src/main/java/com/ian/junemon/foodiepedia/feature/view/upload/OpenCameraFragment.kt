package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.camera.helper.CameraxHelper
import com.ian.junemon.foodiepedia.core.presentation.camera.listener.PhotoListener
import com.ian.junemon.foodiepedia.databinding.FragmentOpenCameraBinding
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_FAST_MILLIS
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_SLOW_MILLIS
import com.ian.junemon.foodiepedia.util.clicks
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class OpenCameraFragment @Inject constructor(
    private val listener: PhotoListener,
    private val cameraxHelper: CameraxHelper
) : BaseFragmentViewBinding<FragmentOpenCameraBinding>() {


    override fun viewCreated() {
        with(binding) {
            clicks(cameraCaptureButton) {
                cameraxHelper.takePhoto()
                // We can only change the foreground Drawable using API level 23+ API
                flashAnimationAfterTakingPicture()
            }
        }
    }

    override fun activityCreated() {
        bindCameraUseCases()
        observeTakePhotoResult()
    }

    private fun bindCameraUseCases() {
        cameraxHelper.startCameraForTakePhoto(
            lifecycleOwner = this as LifecycleOwner,
            preview = cameraxHelper.providePreview(view = binding.viewFinder)
        ) { camera ->
            cameraxHelper.autoFocusPreview(view = binding.viewFinder, camera = camera)
        }
    }

    private fun flashAnimationAfterTakingPicture() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Display flash animation to indicate that photo was captured
            with(binding.root) {
                postDelayed({
                    foreground = ColorDrawable(Color.WHITE)
                    postDelayed(
                        { foreground = null }, ANIMATION_FAST_MILLIS
                    )
                }, ANIMATION_SLOW_MILLIS)
            }
        }
    }

    private fun observeTakePhotoResult() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            listener.photoState.onEach { state ->
                when {
                    state.failed.isNotEmpty() -> Timber.e("failed")
                    state.success.isNotEmpty() -> {
                        val action = OpenCameraFragmentDirections
                            .actionOpenCameraFragmentToSelectImageFragment()
                        navigate(action)
                    }
                }
            }.launchIn(this)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOpenCameraBinding
        get() = FragmentOpenCameraBinding::inflate
}