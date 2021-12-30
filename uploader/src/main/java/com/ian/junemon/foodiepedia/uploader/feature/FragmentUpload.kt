package com.ian.junemon.foodiepedia.uploader.feature

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.core.dagger.factory.viewModelProvider
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.uploader.R
import com.ian.junemon.foodiepedia.uploader.base.BaseFragment
import com.ian.junemon.foodiepedia.uploader.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.uploader.feature.vm.ProfileViewModel
import com.ian.junemon.foodiepedia.uploader.feature.vm.UploadViewModel
import com.ian.junemon.foodiepedia.core.domain.model.FirebaseResult
import com.ian.junemon.foodiepedia.core.domain.model.ProfileResults
import com.ian.junemon.foodiepedia.core.domain.model.FoodRemoteDomain
import com.ian.junemon.foodiepedia.uploader.util.interfaces.LoadImageHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.uploader.util.interfaces.ViewHelper
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Ian Damping on 26,October,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class FragmentUpload : BaseFragment() {
    private val REQUEST_SIGN_IN_PERMISSIONS = 15
    private var isUserAlreadyLogin:Boolean = false

    @Inject
    lateinit var viewHelper: ViewHelper

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var imageHelper: ImageUtilHelper

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var uploadVm: UploadViewModel
    private lateinit var profileVm: ProfileViewModel

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

    private val REQUEST_CAMERA_CODE_PERMISSIONS = 10
    private val REQUIRED_CAMERA_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private val REQUEST_READ_CODE_PERMISSIONS = 3
    private val REQUIRED_READ_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun requestCameraPermissionsGranted() =
        permissionHelper.requestCameraPermissionsGranted(REQUIRED_CAMERA_PERMISSIONS)

    private fun requestReadPermissionsGranted() =
        permissionHelper.requestReadPermissionsGranted(REQUIRED_READ_PERMISSIONS)

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        uploadVm = viewModelProvider(viewModelFactory)
        profileVm = viewModelProvider(viewModelFactory)
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
            if (isUserAlreadyLogin){
                if (selectedUriForFirebase != null) {
                    setDialogShow(false)
                    remoteFoodUpload.foodCategory = binding.spPlaceType.selectedItem.toString()
                    uploadVm.uploadFirebaseData(remoteFoodUpload, selectedUriForFirebase!!)
                        .observe(viewLifecycleOwner, { result ->
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
                        })
                }
            }else{
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
                        if (requestReadPermissionsGranted()) {
                            openImageFromGallery()
                        } else {
                            permissionHelper.run {
                                requestingPermission(
                                    REQUIRED_READ_PERMISSIONS,
                                    REQUEST_READ_CODE_PERMISSIONS
                                )
                            }
                        }
                    }
                    1 -> {
                        if (requestCameraPermissionsGranted()) {
                            imageHelper.openImageFromCamera(this)
                        } else {
                            permissionHelper.run {
                                requestingPermission(
                                    REQUIRED_CAMERA_PERMISSIONS,
                                    REQUEST_CAMERA_CODE_PERMISSIONS
                                )
                            }
                        }
                    }
                }
                dialog.dismiss()
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == REQUEST_READ_CODE_PERMISSIONS) {
                universalCatching {
                    requireNotNull(data)
                    requireNotNull(data.data)

                    uploadVm.setFoodUri(data.data!!)


                    selectedUriForFirebase = data.data!!

                    val bitmap = imageHelper.getBitmapFromGallery(data.data!!)

                    loadingImageHelper.run {
                        if (bitmap != null) {
                            binding.ivPickPhoto.loadWithGlide(bitmap)
                        }
                    }

                    viewHelper.run {
                        binding.btnUnggahFoto.gone()
                        binding.tvInfoUpload.gone()
                        binding.ivPickPhoto.visible()
                    }
                }
            }
        }
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
        profileVm.userData.asLiveData().observe(viewLifecycleOwner){
            when{
                it.errorMessage.isNotEmpty() -> isUserAlreadyLogin = false
                it.user !=null -> {
                    isUserAlreadyLogin = true
                    binding.ivPhotoProfile.visibility = View.VISIBLE
                    remoteFoodUpload.foodContributor = it.user.getDisplayName()
                    loadingImageHelper.run {
                        binding.ivPhotoProfile.loadWithGlide(it.user.getPhotoUrl())
                    }
                }
            }
        }
    }

    private fun observerUri() {
        uploadVm.foodImageUri.observe(viewLifecycleOwner, { imageResult ->
            selectedUriForFirebase = imageResult
        })
    }

    private fun observeViewModelData() {
        uploadVm.foodData.observe(viewLifecycleOwner, { result ->
            if (!result.foodName.isNullOrEmpty() && !result.foodArea.isNullOrEmpty() &&
                !result.foodDescription.isNullOrEmpty() && selectedUriForFirebase != null
            ) {
                viewHelper.run {
                    binding.btnUnggah.visible()
                }
            }

        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.run {
            onRequestingPermissionsResult(
                REQUEST_CAMERA_CODE_PERMISSIONS,
                requestCode,
                grantResults,
                {
                    imageHelper.openImageFromCamera(this@FragmentUpload)
                },
                {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.permission_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()
                })


            onRequestingPermissionsResult(
                REQUEST_READ_CODE_PERMISSIONS,
                requestCode,
                grantResults,
                {
                    openImageFromGallery()
                },
                {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.permission_not_granted),
                        Snackbar.LENGTH_SHORT
                    ).show()
                })
        }
    }

    private fun openImageFromGallery() {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        startActivityForResult(intents, REQUEST_READ_CODE_PERMISSIONS)
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