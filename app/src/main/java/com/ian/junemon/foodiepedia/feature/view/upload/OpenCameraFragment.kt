package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.presentation.camera.ImageCaptureState
import com.ian.junemon.foodiepedia.core.presentation.camera.helper.CameraxHelper
import com.ian.junemon.foodiepedia.databinding.FragmentOpenCameraBinding
import com.ian.junemon.foodiepedia.feature.vm.NavigationViewModel
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_FAST_MILLIS
import com.ian.junemon.foodiepedia.util.FoodConstant.ANIMATION_SLOW_MILLIS
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.core.presentation.camera.listener.PhotoListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class OpenCameraFragment : BaseFragmentViewBinding<FragmentOpenCameraBinding>() {

    @Inject
    lateinit var listener: PhotoListener

    @Inject
    lateinit var cameraxHelper: CameraxHelper


    private val navigationVm: NavigationViewModel by activityViewModels()


    override fun viewCreated() {
        with(binding){
            clicks(cameraCaptureButton){
               cameraxHelper.takePhoto()
                // We can only change the foreground Drawable using API level 23+ API
                flashAnimationAfterTakingPicture()
            }
        }
    }

    override fun activityCreated() {
        bindCameraUseCases()
        observeNavigation()
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

    private fun observeTakePhotoResult(){
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            listener.photoState.onEach { state ->
                when(state){
                    is ImageCaptureState.Failed ->  Timber.e(state.message)
                    ImageCaptureState.Success -> {
                        val action = OpenCameraFragmentDirections
                            .actionOpenCameraFragmentToSelectImageFragment()
                        navigationVm.setNavigationDirection(action)

                    }
                }
            }.launchIn(this)
        }
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            navigationVm.navigationFlow.onEach {
                navigate(it)
            }.launchIn(this)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentOpenCameraBinding
        get() = FragmentOpenCameraBinding::inflate
}