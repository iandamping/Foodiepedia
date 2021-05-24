package com.ian.junemon.foodiepedia.feature.view.upload

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
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
import com.ian.junemon.foodiepedia.feature.vm.NavigationViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.feature.vm.SharedViewModel
import com.ian.junemon.foodiepedia.util.animationSlidDown
import com.ian.junemon.foodiepedia.util.animationSlideUp
import com.ian.junemon.foodiepedia.util.clicks
import com.ian.junemon.foodiepedia.util.createBitmapFromUri
import com.ian.junemon.foodiepedia.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.util.observe
import com.ian.junemon.foodiepedia.util.observeEvent
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
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

    private val navigationVm: NavigationViewModel by activityViewModels()
    private val sharedVm: SharedViewModel by activityViewModels()

    private var bitmap: Bitmap? = null

    private val animationSlideUp by lazy {
        requireContext().animationSlideUp()
    }

    private val animationSlidDown by lazy {
        requireContext().animationSlidDown()
    }

    private lateinit var intentLauncher: ActivityResultLauncher<Intent>

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

        // Create this as a variable in your Fragment class
        intentLauncher = registerForActivityResult(
            StartActivityForResult()){ result ->
            if (result.resultCode == RESULT_OK){
                val intentResult = result.data
                if (intentResult != null){
                    val uriResult = intentResult.data
                    if (uriResult !=null){
                        foodVm.setFoodUri(uriResult)
                    }
                }
            }
        }


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
                    .observe(viewLifecycleOwner, { result ->
                        when (result) {
                            is FirebaseResult.SuccessPush -> {
                                setDialogShow(true)
                                clearUri()
                                val action =
                                    UploadFoodFragmentDirections.actionUploadFoodFragmentToProfileFragment()
                                navigationVm.setNavigationDirection(action)
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
                            imageHelper.openImageFromGallery(intentLauncher)
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
                            navigationVm.setNavigationDirection(action)
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
            universalCatching {
                requireNotNull(data)
                requireNotNull(data.data)

                foodVm.setFoodUri(data.data!!)

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

            bitmap = requireContext().createBitmapFromUri(imageResult)
            // bitmap = imageHelper.getBitmapFromGallery(imageResult)

            binding.ivPickPhoto.setImageBitmap(bitmap)

            with(viewHelper) {
                binding.btnUnggahFoto.gone()
                binding.tvInfoUpload.gone()
                binding.ivPickPhoto.visible()
            }
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
                    navigationVm.setNavigationDirection(action)
                },
                {
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))
                })


            onRequestingPermissionsResult(
                REQUEST_READ_CODE_PERMISSIONS,
                requestCode,
                grantResults,
                {
                    imageHelper.openImageFromGallery(intentLauncher)
                },
                {
                    foodVm.setupSnackbarMessage(getString(R.string.permission_not_granted))
                })
        }
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
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            navigationVm.navigationFlow.onEach {
                navigate(it)
            }.launchIn(this)
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