package com.ian.junemon.foodiepedia.feature.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.RequestSelectGalleryImage
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.feature.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.feature.util.EventObserver
import com.ian.junemon.foodiepedia.feature.vm.FoodViewModel
import com.ian.junemon.foodiepedia.feature.vm.ProfileViewModel
import com.junemon.model.FirebaseResult
import com.junemon.model.domain.FoodRemoteDomain
import kotlinx.android.synthetic.main.fragment_upload.*
import javax.inject.Inject
import kotlin.properties.Delegates

/**
 * Created by Ian Damping on 28,January,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
class UploadFoodFragment : BaseFragment() {
    @Inject
    lateinit var viewHelper: ViewHelper
    @Inject
    lateinit var permissionHelper: PermissionHelper
    @Inject
    lateinit var imageHelper: ImageUtilHelper
    @Inject
    lateinit var foodVm: FoodViewModel
    @Inject
    lateinit var profileVm: ProfileViewModel

    private val animationSlideUp by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.slide_in_up) }

    private val animationSlidDown by lazy { AnimationUtils.loadAnimation(requireContext(),
        R.anim.slide_in_down) }

    private val remoteFoodUpload: FoodRemoteDomain = FoodRemoteDomain()
    private var selectedUriForFirebase by Delegates.notNull<Uri>()
    private var isPermisisonGranted by Delegates.notNull<Boolean>()

    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        setBaseDialog()
        permissionHelper.getAllPermission(requireActivity()){
            isPermisisonGranted = it

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        consumeViewModelData()
        consumeProfileData()
        observeViewModelData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            foodViewModel = foodVm
            rlUploadImage.animation = animationSlidDown
            llInsertData.animation = animationSlideUp
        }

    }

    private fun FragmentUploadBinding.initView() {
            val allFoodCategory: Array<String> =
                requireContext().resources.getStringArray(R.array.food_category)
            val arrayTypeFoodSpinnerAdapter: ArrayAdapter<String>? =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, allFoodCategory)
            arrayTypeFoodSpinnerAdapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spPlaceType.adapter = arrayTypeFoodSpinnerAdapter

            btnUnggahFoto.setOnClickListener {
                openGalleryAndCamera(isPermisisonGranted)
            }

            btnBack.setOnClickListener {
               findNavController().navigateUp()

            }
    }

    private fun openGalleryAndCamera(status: Boolean) {
        universalCatching {
            require(status)
            val options = arrayOf("Gallery", "Camera")
            AlertDialog.Builder(context)
                .setItems(options) { dialog, which ->
                    when (which) {
                        0 -> imageHelper.openImageFromGallery(this)
                        1 -> imageHelper.openImageFromCamera(this)
                    }
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == FragmentActivity.RESULT_OK) {
            if (requestCode == RequestSelectGalleryImage) {
                universalCatching {
                    requireNotNull(data)
                    requireNotNull(data.data)

                    foodVm.setFoodUri(data.data!!)

                    val bitmap = imageHelper.getBitmapFromGallery(requireContext(), data.data!!)
                    viewHelper.run {
                        btnUnggahFoto.gone()
                        tvInfoUpload.gone()
                        ivPickPhoto.visible()
                    }
                    binding.ivPickPhoto.setImageBitmap(bitmap)
                }
            } else {
                ilegallStateCatching {

                    val bitmap = imageHelper.decodeSampledBitmapFromFile(
                        imageHelper.createImageFileFromPhoto(requireContext()) {
                            foodVm.setFoodUri(it)
                        }
                    )
                    viewHelper.run {
                        btnUnggahFoto.gone()
                        tvInfoUpload.gone()
                        ivPickPhoto.visible()
                    }
                    binding.ivPickPhoto.setImageBitmap(bitmap)
                }
            }
        }
    }

    private fun consumeViewModelData() {
        foodVm.run {
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
        profileVm.getUser().observe(viewLifecycleOwner, Observer {
                remoteFoodUpload.foodContributor = it.nameUser
        })
    }

    private fun observeViewModelData() {
        foodVm.foodData.observe(viewLifecycleOwner, Observer { result ->
            foodVm.foodImageUri.observe(viewLifecycleOwner, Observer { imageResult ->
                selectedUriForFirebase = imageResult

                if (!result.foodName.isNullOrEmpty() && !result.foodArea.isNullOrEmpty() &&
                    !result.foodDescription.isNullOrEmpty() && imageResult != null
                ) {
                    viewHelper.run {
                        binding.btnUnggah.visible()
                    }
                }
            })

            binding.btnUnggah.setOnClickListener {
                setDialogShow(false)
                remoteFoodUpload.foodCategory = binding.spPlaceType.selectedItem.toString()
                foodVm.uploadFirebaseData(result, selectedUriForFirebase)
                    .observe(viewLifecycleOwner, Observer { result ->
                        when (result) {
                            is FirebaseResult.SuccessPush -> {
                                setDialogShow(true)
                                findNavController().navigateUp()
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
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}