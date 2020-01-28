package com.ian.junemon.foodiepedia.food.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.ian.junemon.foodiepedia.core.presentation.PresentationConstant.RequestSelectGalleryImage
import com.ian.junemon.foodiepedia.core.presentation.base.BaseFragment
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.PermissionHelper
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ViewHelper
import com.ian.junemon.foodiepedia.food.databinding.FragmentUploadBinding
import com.ian.junemon.foodiepedia.food.di.sharedFoodComponent
import com.ian.junemon.foodiepedia.food.view.adapter.UploadAdapter
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

    private var isPermisisonGranted by Delegates.notNull<Boolean>()
    private var selectedUriForFirebase by Delegates.notNull<Uri>()
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        sharedFoodComponent().inject(this)
        super.onAttach(context)
        ilegallArgumenCatching {
            checkNotNull(activity)
            permissionHelper.getAllPermission(activity!!) {
                isPermisisonGranted = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            initView()
            tabMainPage.setupWithViewPager(vpMainPage)
            vpMainPage.adapter = UploadAdapter(childFragmentManager)

        }
        return binding.root
    }

    private fun FragmentUploadBinding.initView() {
        apply {
            btnUnggahFoto.setOnClickListener {
                openGalleryAndCamera(isPermisisonGranted)
            }
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
                    checkNotNull(context)
                    selectedUriForFirebase = data.data!!
                    val bitmap = imageHelper.getBitmapFromGallery(context!!, data.data!!)
                    viewHelper.run {
                        btnUnggahFoto.gone()
                        tvInfoUpload.gone()
                        ivPickPhoto.visible()
                    }
                    binding.ivPickPhoto.setImageBitmap(bitmap)
                }
            } else {
                ilegallStateCatching {
                    checkNotNull(context)
                    val bitmap = imageHelper.decodeSampledBitmapFromFile(
                        imageHelper.createImageFileFromPhoto(context!!) {
                            selectedUriForFirebase = it
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
}