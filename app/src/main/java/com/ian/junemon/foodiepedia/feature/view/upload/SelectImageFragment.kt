package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraXFileDirectory
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxPhotoFile
import com.ian.junemon.foodiepedia.databinding.FragmentSelectImageBinding
import com.ian.junemon.foodiepedia.feature.vm.NavigationViewModel
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.observeEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 08,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class SelectImageFragment : BaseFragmentViewBinding<FragmentSelectImageBinding>() {

    @Inject
    @CameraxPhotoFile
    lateinit var photoFile: File

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    private val sharedVm: SharedViewModel by activityViewModels()
    private val navigationVm: NavigationViewModel by activityViewModels()

    override fun viewCreated() {
        with(binding) {
            val savedUri = Uri.fromFile(photoFile)

            val imageFile: File = File(photoFile.absolutePath)
            val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

            with(loadingImageHelper){
                ivImage.loadWithGlide(bitmap)
            }
            clicks(ivImageSelect) {
                sharedVm.setPassedUri(savedUri.toString())

                val action =SelectImageFragmentDirections.actionSelectImageFragmentToUploadFoodFragment()
                navigationVm.setNavigationDirection(action)
            }
            clicks(ivImageDelete) {
                imageFile.delete()
                MediaScannerConnection.scanFile(
                    requireContext(), arrayOf(photoFile.absolutePath), null, null
                )
                navigateUp()
            }
        }
    }

    override fun activityCreated() {
        observeNavigation()
    }

    private fun observeNavigation() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            navigationVm.navigationFlow.onEach {
                navigate(it)
            }.launchIn(this)
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectImageBinding
        get() = FragmentSelectImageBinding::inflate
}