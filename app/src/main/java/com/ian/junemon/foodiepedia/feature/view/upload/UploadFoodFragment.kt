package com.ian.junemon.foodiepedia.feature.view.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentDataBinding
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.presentation.view.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.view.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.view.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class UploadFoodFragment @Inject constructor(
    private val viewHelper: ViewHelper,
    private val permissionHelper: PermissionHelper,
    private val imageHelper: ImageUtilHelper
) : BaseFragmentDataBinding<FragmentUploadBinding>() {

    private val foodVm: FoodViewModel by viewModels()
    private val profileVm: ProfileViewModel by viewModels()

    private val sharedVm: SharedViewModel by activityViewModels()

    private var bitmap: Bitmap? = null

    private val animationSlideUp by lazy {
        requireContext().animationSlideUp()
    }

    private val animationSlidDown by lazy {
        requireContext().animationSlidDown()
    }

    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var openGalleryPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var openCameraPermissionLauncher: ActivityResultLauncher<String>

    private val remoteFoodUpload: FoodRemoteDomain = FoodRemoteDomain()
    private var selectedUriForFirebase: Uri? = null

    private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val REQUIRED_READ_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun requestCameraPermissionsGranted() =
        permissionHelper.requestGranted(REQUIRED_CAMERA_PERMISSIONS)

    private fun requestReadPermissionsGranted() =
        permissionHelper.requestGranted(REQUIRED_READ_PERMISSIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Create this as a variable in your Fragment class
        intentLauncher = registerForActivityResult(
            StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentResult = result.data
                if (intentResult != null) {
                    val uriResult = intentResult.data
                    if (uriResult != null) {
                        foodVm.setFoodUri(uriResult)
                    }
                }
            }
        }

        openGalleryPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    imageHelper.openImageFromGallery(intentLauncher)
                } else {
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))

                }
            }

        openCameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    val action =
                        UploadFoodFragmentDirections.actionUploadFoodFragmentToOpenCameraFragment()
                    navigate(action)
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))

                }
            }
    }

    override fun viewCreated() {
        initOnBackPressed()
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            foodViewModel = foodVm
            rlUploadImage.animation = animationSlidDown
            llInsertData.animation = animationSlideUp
        }
        observeProfileUiState()
    }

    override fun activityCreated() {
        observerUri()
        consumeViewModelData()
        observeViewModelData()
        observeViewEffect()
        consumeSharedUri()
    }

    private fun FragmentUploadBinding.initView() {
        val allFoodCategory: Array<String> =
            requireContext().resources.getStringArray(R.array.food_category)
        val arrayTypeFoodSpinnerAdapter: ArrayAdapter<String>? =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allFoodCategory)
        arrayTypeFoodSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPlaceType.adapter = arrayTypeFoodSpinnerAdapter

        clicks(btnUnggahFoto) {
            openGalleryAndCamera()
        }

        clicks(btnBack) {
            navigateUp()
        }

        clicks(btnUnggah) {
            if (selectedUriForFirebase != null) {
                setDialogShow(false)
                remoteFoodUpload.foodCategory = binding.spPlaceType.selectedItem.toString()
                foodVm.uploadFirebaseData(remoteFoodUpload, selectedUriForFirebase!!)
                    .observe(viewLifecycleOwner) { result ->
                        when (result) {
                            is FirebaseResult.SuccessPush -> {
                                setDialogShow(true)
                                clearUri()
                                val action =
                                    UploadFoodFragmentDirections.actionUploadFoodFragmentToProfileFragment()
                                navigate(action)
                            }
                            is FirebaseResult.ErrorPush -> {
                                setDialogShow(true)
                                clearUri()
                                foodVm.setupSnackbarMessage("Something happen ${result.exception}")
                            }
                        }
                    }
            }

        }
    }

    private fun openGalleryAndCamera() {
        val options = arrayOf("Gallery", "Camera")
        AlertDialog.Builder(context)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        when {
                            requestReadPermissionsGranted() -> imageHelper.openImageFromGallery(
                                intentLauncher
                            )

                            else -> openGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }

                    }
                    1 -> {
                        when {
                            requestCameraPermissionsGranted() -> {
                                val action =
                                    UploadFoodFragmentDirections.actionUploadFoodFragmentToOpenCameraFragment()
                                navigate(action)
                            }
                            else -> openCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
                dialog.dismiss()
            }.show()
    }


    private fun consumeViewModelData() {
        with(foodVm) {
            observingEditText(viewLifecycleOwner, etFoodName) {
                remoteFoodUpload.foodName = it
                setFood(remoteFoodUpload)
            }
            observingEditText(viewLifecycleOwner, etFoodArea) {
                remoteFoodUpload.foodArea = it
                setFood(remoteFoodUpload)
            }

            observingEditText(viewLifecycleOwner, etFoodDescription) {
                remoteFoodUpload.foodDescription = it
                setFood(remoteFoodUpload)
            }
        }
    }

    private fun observeProfileUiState() {
        profileVm.userData.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.user != null -> {
                    remoteFoodUpload.foodContributor = it.user.getDisplayName()
                }
                it.errorMessage.isNotEmpty() -> {
                    foodVm.setupSnackbarMessage(it.errorMessage)
                }
            }
        }
    }

    private fun observerUri() {
        observe(foodVm.foodImageUri) { imageResult ->
            selectedUriForFirebase = imageResult

            bitmap = requireContext().createBitmapFromUri(imageResult)

            binding.ivPickPhoto.setImageBitmap(bitmap)

            with(viewHelper) {
                gone(binding.btnUnggahFoto)
                gone(binding.tvInfoUpload)
                visible(binding.ivPickPhoto)
            }
        }
    }

    private fun observeViewModelData() {
        observe(foodVm.foodData) { result ->
            if (!result.foodName.isNullOrEmpty() && !result.foodArea.isNullOrEmpty() &&
                !result.foodDescription.isNullOrEmpty() && selectedUriForFirebase != null
            ) {
                viewHelper.visible(binding.btnUnggah)
            }

        }
    }

    private fun observeViewEffect() {
        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }
        }
    }

    private fun consumeSharedUri() {
        observeEvent(sharedVm.passedUri) {
            val savedUri = Uri.parse(it)
            foodVm.setFoodUri(savedUri)
        }
    }

    private fun initOnBackPressed() {
        onBackPressed {
            navigateUp()
        }
    }

    private fun clearUri() {
        bitmap?.recycle()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUploadBinding
        get() = FragmentUploadBinding::inflate
}