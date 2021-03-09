package com.ian.junemon.foodiepedia.feature.view.upload

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.base.BaseFragmentDataBinding
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.util.DataConstant.RequestOpenCamera
import com.ian.junemon.foodiepedia.core.util.DataConstant.RequestSelectGalleryImage
import com.ian.junemon.foodiepedia.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.createBitmapFromUri
import com.ian.junemon.foodiepedia.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import javax.inject.Inject

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadFoodFragment : BaseFragmentDataBinding<FragmentUploadBinding>() {
    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var imageHelper: ImageUtilHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var foodVm: FoodViewModel
    private lateinit var profileVm: ProfileViewModel

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper

    private val sharedVm: SharedViewModel by activityViewModels()

    private var bitmap: Bitmap? = null

    private val animationSlideUp by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_up
        )
    }

    private val animationSlidDown by lazy {
        AnimationUtils.loadAnimation(
            requireContext(),
            R.anim.slide_in_down
        )
    }

    private val remoteFoodUpload: FoodRemoteDomain = FoodRemoteDomain()
    private var selectedUriForFirebase: Uri? = null

    private val REQUEST_CAMERA_CODE_PERMISSIONS = RequestOpenCamera
    private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val REQUEST_READ_CODE_PERMISSIONS = RequestSelectGalleryImage
    private val REQUIRED_READ_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun requestCameraPermissionsGranted() =
        permissionHelper.requestCameraPermissionsGranted(REQUIRED_CAMERA_PERMISSIONS)

    private fun requestReadPermissionsGranted() =
        permissionHelper.requestReadPermissionsGranted(REQUIRED_READ_PERMISSIONS)

    override fun viewCreated() {
        initOnBackPressed()
        foodVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            foodViewModel = foodVm
            rlUploadImage.animation = animationSlidDown
            llInsertData.animation = animationSlideUp
        }
    }

    override fun activityCreated() {
        observerUri()
        consumeViewModelData()
        consumeProfileData()
        observeViewModelData()
        observeViewEffect()
        obvserveNavigation()
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
            val action = UploadFoodFragmentDirections.actionUploadFoodFragmentToHomeFragment()
            foodVm.setNavigate(action)
        }

        clicks(btnUnggah) {
            if (selectedUriForFirebase != null) {
                setDialogShow(false)
                remoteFoodUpload.foodCategory = binding.spPlaceType.selectedItem.toString()
                foodVm.uploadFirebaseData(remoteFoodUpload, selectedUriForFirebase!!)
                    .observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is FirebaseResult.SuccessPush -> {
                                setDialogShow(true)
                                clearUri()
                                val action = UploadFoodFragmentDirections.actionUploadFoodFragmentToHomeFragment()
                                foodVm.setNavigate(action)
                            }
                            is FirebaseResult.ErrorPush -> {
                                setDialogShow(true)
                                clearUri()
                                foodVm.setupSnackbarMessage("Something happen ${result.exception}")
                            }
                        }
                    })
            }

        }
    }

    private fun openGalleryAndCamera() {
        val options = arrayOf("Gallery", "Camera")
        AlertDialog.Builder(context)
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        if (requestReadPermissionsGranted()) {
                            openImageFromGallery()
                        } else {
                            with(permissionHelper) {
                                requestingPermission(
                                    REQUIRED_READ_PERMISSIONS,
                                    REQUEST_READ_CODE_PERMISSIONS
                                )
                            }
                        }
                    }
                    1 -> {
                        if (requestCameraPermissionsGranted()) {
                            val action =
                                UploadFoodFragmentDirections.actionUploadFoodFragmentToOpenCameraFragment()
                            foodVm.setNavigate(action)
                        } else {
                            with(permissionHelper) {
                                requestingPermission(
                                    REQUIRED_CAMERA_PERMISSIONS,
                                    REQUEST_CAMERA_CODE_PERMISSIONS
                                )
                            }
                        }
                    }
                }
                dialog.dismiss()
            }.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == REQUEST_READ_CODE_PERMISSIONS) {
                universalCatching {
                    requireNotNull(data)
                    requireNotNull(data.data)

                    foodVm.setFoodUri(data.data!!)

                    bitmap = imageHelper.getBitmapFromGallery(data.data!!)

                    with(loadingImageHelper) {
                        if (bitmap != null) {
                            binding.ivPickPhoto.loadWithGlide(bitmap!!)
                        }
                    }

                    with(viewHelper) {
                        binding.btnUnggahFoto.gone()
                        binding.tvInfoUpload.gone()
                        binding.ivPickPhoto.visible()
                    }
                }
            }
        }
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

    private fun consumeProfileData() {
        observe(profileVm.getUserProfile()) {
            if (it is ProfileResults.Success) {
                remoteFoodUpload.foodContributor = it.data.getDisplayName()
            }
        }
    }

    private fun observerUri() {
        observe(foodVm.foodImageUri) { imageResult ->
            selectedUriForFirebase = imageResult
        }
    }

    private fun observeViewModelData() {
        observe(foodVm.foodData) { result ->
            if (!result.foodName.isNullOrEmpty() && !result.foodArea.isNullOrEmpty() &&
                !result.foodDescription.isNullOrEmpty() && selectedUriForFirebase != null
            ) {
                with(viewHelper) {
                    binding.btnUnggah.visible()
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        with(permissionHelper) {
            onRequestingPermissionsResult(
                REQUEST_CAMERA_CODE_PERMISSIONS,
                requestCode,
                grantResults,
                {
                    val action =
                        UploadFoodFragmentDirections.actionUploadFoodFragmentToOpenCameraFragment()
                    foodVm.setNavigate(action)
                },
                {
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))
                })


            onRequestingPermissionsResult(
                REQUEST_READ_CODE_PERMISSIONS,
                requestCode,
                grantResults,
                {
                    openImageFromGallery()
                },
                {
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))
                })
        }
    }

    private fun openImageFromGallery() {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        startActivityForResult(intents, REQUEST_READ_CODE_PERMISSIONS)
    }

    private fun observeViewEffect() {
        observe(foodVm.snackbar) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                foodVm.onSnackbarShown()
            }
        }
    }

    private fun obvserveNavigation() {
        observeEvent(foodVm.navigateEvent) {
            navigate(it)
        }
    }

    private fun consumeSharedUri() {
        observeEvent(sharedVm.passedUri) {
            val savedUri = Uri.parse(it)

            foodVm.setFoodUri(savedUri)

            bitmap = requireContext().createBitmapFromUri(savedUri)

            binding.ivPickPhoto.setImageBitmap(bitmap)

            with(viewHelper) {
                binding.btnUnggahFoto.gone()
                binding.tvInfoUpload.gone()
                binding.ivPickPhoto.visible()
            }
        }
    }

    private fun initOnBackPressed() {
        onBackPressed {
            val action = UploadFoodFragmentDirections.actionUploadFoodFragmentToHomeFragment()
            foodVm.setNavigate(action)
        }
    }

    private fun clearUri() {
        bitmap?.recycle()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUploadBinding
        get() = FragmentUploadBinding::inflate
}