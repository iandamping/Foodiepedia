package com.ian.junemon.foodiepedia.feature.view.upload

import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.ian.junemon.foodiepedia.base.BaseFragmentViewBinding
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxPhotoFile
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.databinding.FragmentSelectImageBinding
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.clicks
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

/**
 * Created by Ian Damping on 08,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class SelectImageFragment @Inject constructor(
    private val loadingImageHelper: LoadImageHelper,
    @CameraxPhotoFile private val photoFile: File
) : BaseFragmentViewBinding<FragmentSelectImageBinding>() {

    private val sharedVm: SharedViewModel by activityViewModels()

    override fun viewCreated() {
        with(binding) {
            val savedUri = Uri.fromFile(photoFile)

            val imageFile: File = File(photoFile.absolutePath)
            val bitmap = BitmapFactory.decodeFile(photoFile.absolutePath)

            loadingImageHelper.loadWithGlide(ivImage, bitmap)

            clicks(ivImageSelect) {
                sharedVm.setPassedUri(savedUri.toString())

                val action =
                    SelectImageFragmentDirections.actionSelectImageFragmentToUploadFoodFragment()
                navigate(action)
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
    }


    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSelectImageBinding
        get() = FragmentSelectImageBinding::inflate
}