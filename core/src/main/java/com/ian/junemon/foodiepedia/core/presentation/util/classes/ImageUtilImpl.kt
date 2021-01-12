package com.ian.junemon.foodiepedia.core.presentation.util.classes

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.StorageReference
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.presentation.util.interfaces.ImageUtilHelper
import com.ian.junemon.foodiepedia.core.util.DataConstant.RequestOpenCamera
import com.ian.junemon.foodiepedia.core.util.DataConstant.RequestSelectGalleryImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.anko.progressDialog
import timber.log.Timber
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import javax.inject.Inject

/**
 * Created by Ian Damping on 04,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class ImageUtilImpl @Inject constructor(
    private val storagePlaceReference: StorageReference,
    private val ctx: Context
) :
    ImageUtilHelper {

    private val saveCaptureImagePath = "picture" + System.currentTimeMillis() + ".jpeg"
    private val saveFilterImagePath = "filterImage" + System.currentTimeMillis() + ".jpeg"
    private val maxWidth = 612
    private val maxHeight = 816

    override fun getBitmapFromAssets(
        fileName: String,
        widthImage: Int,
        heightImage: Int
    ): Bitmap? {
        val assetManager: AssetManager = ctx.assets
        val inputStreams: InputStream
        try {
            val options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true

            inputStreams = assetManager.open(fileName)
            // calculate sample size
            options.inSampleSize = calculateSampleSize(options, widthImage, heightImage)
            // decode bitmpat with samplesize set
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(inputStreams, null, options)
        } catch (e: IOException) {
            Timber.tag("### log helper ##").e(e)
        }
        return null
    }

    override fun getBitmapFromGallery(path: Uri): Bitmap? {
        val filePathColum = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            ctx.applicationContext?.contentResolver?.query(path, filePathColum, null, null, null)
        cursor?.moveToFirst()

        val columnIndex: Int? = cursor?.getColumnIndex(filePathColum[0])
        val picturePath = columnIndex?.let { cursor.getString(it) }
        cursor?.close()

        val options: BitmapFactory.Options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(picturePath, options)

        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(picturePath, options)
    }

    override fun saveImage(views: View, bitmap: Bitmap?) {
        try {
            requireNotNull(bitmap) {
                "Bitmap that needs to save is null"
            }
            val pictureDirectory = Environment.getExternalStorageDirectory()
            val imageFile = File(pictureDirectory, saveFilterImagePath)
            val quality = 100
            val outputStream = FileOutputStream(imageFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            openImageFromSnackbar(views, imageFile)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        voidCustomMediaScannerConnection(saveFilterImagePath)
    }

    override fun Fragment.saveImage(scope: CoroutineScope, views: View, imageUrl: String?) {
        scope.launch {
            try {
                requireNotNull(imageUrl) {
                    "picture to share is null"
                }
                withContext(Dispatchers.IO) {
                    val bitmap = Glide.with(this@saveImage)
                        .asBitmap()
                        .load(imageUrl)
                        .submit(512, 512)
                        .get()
                    if (bitmap != null) {
                        saveImage(views, bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun saveFirebaseImageToGallery(
        views: View,
        lastPathSegment: String?
    ) {
        try {
            requireNotNull(lastPathSegment) {
                "Image last path is null"
            }
            val dialogs = ctx.progressDialog(
                views.context.resources?.getString(R.string.please_wait),
                ctx.resources.getString(R.string.downloading_image)
            )
            val spaceRef = storagePlaceReference.child(lastPathSegment)
            val pictureDirectory = Environment.getExternalStorageDirectory()
            val imageFile = File(pictureDirectory, saveFilterImagePath)
            /*
            kita memerlukan variable file agar menampung image dari firebase storage kita
            perhatikan juga pada bagian storage refence nya kita memerlukan nama file / last path segment image kita agar tau
            image mana yg akan di download
             */
            spaceRef.getFile(imageFile).addOnProgressListener {
                dialogs.show()
                val progress = 100.0 * it.bytesTransferred / it.totalByteCount
                dialogs.progress = progress.toInt()
            }.addOnSuccessListener {
                if (it.task.isSuccessful) {
                    voidCustomMediaScannerConnection(saveFilterImagePath)
                    dialogs.dismiss()
                    openImageFromSnackbar(views, imageFile)
                }
            }.addOnFailureListener {
                Timber.tag("### log helper ##").e("local tem file not created ")
            }
        } catch (e: Exception) {
            Timber.tag("### log helper ##").e(e)
        }
    }

    // decode File into Bitmap and compress it
    override fun decodeSampledBitmapFromFile(imageFile: File): Bitmap {
        // First decode with inJustDecodeBounds=true to check dimensions
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFile.absolutePath, options)

        // Calculate inSampleSize
        options.inSampleSize = calculateSampleSize(options, maxWidth, maxHeight)

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false

        var scaledBitmap = BitmapFactory.decodeFile(imageFile.absolutePath, options)

        // check the rotation of the image and display it properly
        val exif = ExifInterface(imageFile.absolutePath)
        val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
        val matrix = Matrix()
        when (orientation) {
            6 -> {
                matrix.postRotate(90F)
            }
            3 -> {
                matrix.postRotate(180F)
            }
            8 -> {
                matrix.postRotate(270F)
            }
        }
        scaledBitmap = Bitmap.createBitmap(
            scaledBitmap,
            0,
            0,
            scaledBitmap.width,
            scaledBitmap.height,
            matrix,
            true
        )
        return scaledBitmap
    }

    private fun openImageFromSnackbar(views: View, imageFile: File) {
        val snackbar = Snackbar
            .make(views, "Image saved to gallery!", Snackbar.LENGTH_LONG)
            .setAction("OPEN") {
                val i = Intent(Intent.ACTION_VIEW)
                i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                val uri =
                    FileProvider.getUriForFile(
                        ctx,
                        ctx.resources.getString(R.string.package_name),
                        imageFile
                    )
                i.setDataAndType(uri, "image/*")
                ctx.startActivity(i)
            }
        snackbar.show()
    }

    private fun calculateSampleSize(
        options: BitmapFactory.Options,
        requiredWidth: Int,
        requiredHeight: Int
    ): Int {
        val height = options.outHeight
        val widht = options.outWidth
        var inSampleSize = 1

        if (height > requiredHeight || widht > requiredHeight) {
            val halfHeight = height / 2
            val halfWidth = widht / 2

            while ((halfHeight / inSampleSize) >= requiredHeight && (halfWidth / inSampleSize) >= requiredWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    override fun bitmapToFile(bitmap: Bitmap?): File {
        val f = File(ctx.cacheDir, "image_uploads")
        f.createNewFile()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val fos = FileOutputStream(f)
        fos.write(byteArray)
        fos.flush()
        fos.close()
        return f
    }

    override fun openImageFromGallery(fragment: Fragment) {
        val intents = Intent(Intent.ACTION_PICK)
        intents.type = "image/*"
        fragment.startActivityForResult(intents, RequestSelectGalleryImage)
    }

    override fun openImageFromCamera(fragment: Fragment) {
        val pictureUri: Uri = FileProvider.getUriForFile(
            ctx,
            fragment.getString(R.string.package_name),
            createImageFileFromPhoto()
        )
        val intents = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intents.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri)
        intents.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        fragment.startActivityForResult(intents, RequestOpenCamera)
    }

    override fun createImageFileFromPhoto(uri: (Uri) -> Unit): File {
        return nonVoidCustomMediaScannerConnection(saveCaptureImagePath, uri)
    }

    private fun createImageFileFromPhoto(): File {
        return nonVoidCustomMediaScannerConnection(saveCaptureImagePath)
    }

    private fun nonVoidCustomMediaScannerConnection(
        paths: String,
        uriToPassed: (Uri) -> Unit
    ): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Timber.i("Scanned $path:")
            Timber.i("uri=$uri")
            uriToPassed(uri)
        }
        return passingFile
    }

    private fun nonVoidCustomMediaScannerConnection(paths: String): File {
        val directory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Timber.i("Scanned $path:")
            Timber.i("uri=$uri")
        }
        return passingFile
    }

    private fun voidCustomMediaScannerConnection(paths: String) {
        val directory = Environment.getExternalStorageDirectory()
        val passingFile = File(directory, paths)
        MediaScannerConnection.scanFile(ctx, arrayOf(passingFile.toString()), null) { path, uri ->
            Timber.i("Scanned $path:")
            Timber.i("uri=$uri")
        }
    }
}
