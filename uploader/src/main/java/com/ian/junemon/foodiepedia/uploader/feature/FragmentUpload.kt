package com.ian.junemon.foodiepedia.uploader.feature

import android.Manifest
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.core.presentation.view.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.view.LoadImageHelper
import com.ian.junemon.foodiepedia.core.presentation.view.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.view.ViewHelper
import com.ian.junemon.foodiepedia.uploader.R
import com.ian.junemon.foodiepedia.uploader.base.BaseFragment
import com.ian.junemon.foodiepedia.uploader.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.uploader.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.uploader.feature.vm.UploadViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@AndroidEntryPoint
class FragmentUpload : BaseFragment() {
    private val REQUEST_SIGN_IN_PERMISSIONS = 15
    private var isUserAlreadyLogin: Boolean = false

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var imageHelper: ImageUtilHelper

    private val uploadVm: UploadViewModel by viewModels()
    private val profileVm: ProfileViewModel by viewModels()

    @Inject
    lateinit var loadingImageHelper: LoadImageHelper



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

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    private lateinit var intentLauncher: ActivityResultLauncher<Intent>
    private lateinit var openGalleryPermissionLauncher: ActivityResultLauncher<String>
    private lateinit var openCameraPermissionLauncher: ActivityResultLauncher<String>

    private val REQUEST_CAMERA_CODE_PERMISSIONS = 10
    private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val REQUEST_READ_CODE_PERMISSIONS = 3
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
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val intentResult = result.data
                if (intentResult!=null){
                    val uriResult = intentResult.data
                    if (uriResult != null) {
                        uploadVm.setFoodUri(uriResult)
                        selectedUriForFirebase = uriResult
                        val bitmap = imageHelper.getBitmapFromGallery(uriResult)
                        loadingImageHelper.loadWithGlide(binding.ivPickPhoto,bitmap)

                        with(viewHelper){
                            gone(binding.btnUnggahFoto)
                            gone(binding.tvInfoUpload)
                            visible(binding.ivPickPhoto)
                        }
                    }
                }
            }
        }
        openGalleryPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    imageHelper.openImageFromGallery(intentLauncher)
                } else {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.permission_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
            }

        openCameraPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    imageHelper.openImageFromCamera(intentLauncher)
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                    Snackbar.make(
                        binding.root,
                        getString(R.string.permission_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()

                }
            }
    }

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun viewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            uploadViewModel = uploadVm
            rlUploadImage.animation = animationSlidDown
            llInsertData.animation = animationSlideUp
        }
        consumeProfileData()

    }

    override fun destroyView() {
        _binding = null
    }

    override fun activityCreated() {
        observerUri()
        consumeViewModelData()
        observeViewModelData()

    }

    private fun FragmentUploadBinding.initView() {
        val allFoodCategory: Array<String> =
            requireContext().resources.getStringArray(R.array.food_category)
        val arrayTypeFoodSpinnerAdapter: ArrayAdapter<String>? =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allFoodCategory)
        arrayTypeFoodSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spPlaceType.adapter = arrayTypeFoodSpinnerAdapter

        btnUnggahFoto.setOnClickListener {
            openGalleryAndCamera()
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()

        }

        btnUnggah.setOnClickListener {
            if (isUserAlreadyLogin) {
                if (selectedUriForFirebase != null) {
                    setDialogShow(false)
                    remoteFoodUpload.foodCategory = binding.spPlaceType.selectedItem.toString()
                    uploadVm.uploadFirebaseData(remoteFoodUpload, selectedUriForFirebase!!)
                        .observe(viewLifecycleOwner) { result ->
                            when (result) {
                                is FirebaseResult.SuccessPush -> {
                                    setDialogShow(true)
                                    requireActivity().finish()
                                    startActivity(requireActivity().intent)
                                    requireActivity().overridePendingTransition(0, 0)
                                }
                                is FirebaseResult.ErrorPush -> {
                                    setDialogShow(true)
                                    Snackbar.make(
                                        binding.root,
                                        "Something happen ${result.exception}",
                                        Snackbar.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                }
            } else {
                fireSignIn()
            }
        }
    }

    private fun openGalleryAndCamera() {
        val options = arrayOf("Gallery", "Camera")
        AlertDialog.Builder(requireContext())
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> {
                        when{
                            requestReadPermissionsGranted() -> imageHelper.openImageFromGallery(intentLauncher)

                            else -> openGalleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }

                    }
                    1 -> {
                        when{
                            requestCameraPermissionsGranted() -> {
                                imageHelper.openImageFromCamera(intentLauncher)
                            }
                            else -> openCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    }
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun consumeViewModelData() {
        with(uploadVm) {
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
        profileVm.userData.asLiveData().observe(viewLifecycleOwner) {
            when {
                it.errorMessage.isNotEmpty() -> isUserAlreadyLogin = false
                it.user != null -> {
                    isUserAlreadyLogin = true
                    binding.ivPhotoProfile.visibility = View.VISIBLE
                    remoteFoodUpload.foodContributor = it.user.getDisplayName()
                    loadingImageHelper.loadWithGlide(binding.ivPhotoProfile,it.user.getPhotoUrl())

                }
            }
        }
    }

    private fun observerUri() {
        uploadVm.foodImageUri.observe(viewLifecycleOwner) { imageResult ->
            selectedUriForFirebase = imageResult
        }
    }

    private fun observeViewModelData() {
        uploadVm.foodData.observe(viewLifecycleOwner) { result ->
            if (!result.foodName.isNullOrEmpty() && !result.foodArea.isNullOrEmpty() &&
                !result.foodDescription.isNullOrEmpty() && selectedUriForFirebase != null
            ) {
                viewHelper.visible(binding.btnUnggah)
            }

        }
    }

    private fun fireSignIn() {
        lifecycleScope.launch {
            val signInIntent = profileVm.initSignIn()
            startActivityForResult(signInIntent, REQUEST_SIGN_IN_PERMISSIONS)
        }
    }

    private fun fireSignOut() {
        lifecycleScope.launch {
            profileVm.initLogout {
                isUserAlreadyLogin = false
                binding.ivPhotoProfile.visibility = View.GONE
            }
        }
    }
}