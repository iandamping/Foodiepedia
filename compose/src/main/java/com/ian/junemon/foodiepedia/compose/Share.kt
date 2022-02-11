package com.ian.junemon.foodiepedia.compose

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.core.content.FileProvider
import coil.imageLoader
import coil.request.ImageRequest
import com.ian.junemon.foodiepedia.core.R
import java.io.File
import java.io.FileOutputStream

fun intentShareImageAndText(
    context: Context,
    tittle: String?,
    message: String?,
    imageUrl: String?
) {
    try {
        val singleInstanceImageLoader = context.imageLoader
        val imageRequest = ImageRequest.Builder(context)
            .data(requireNotNull(imageUrl))
            .target { result ->
                val bitmap = (result as BitmapDrawable).bitmap

                if (getUriImageToShare(context, bitmap) != null) {

                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "image/*"
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, getUriImageToShare(context, bitmap))
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, requireNotNull(tittle) {
                        "tittle to share is null"
                    })
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, requireNotNull(message) {
                        "message to share is null"
                    })
                    sharingIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                    context.startActivity(
                        Intent.createChooser(
                            sharingIntent,
                            "Share Image"
                        )
                    )
                }
            }
            .build()


        singleInstanceImageLoader.enqueue(imageRequest)


    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// Retrieving the url to share
private fun getUriImageToShare(context: Context, bitmap: Bitmap): Uri? {
    val imagefolder: File = File(context.cacheDir, "images")
    var uri: Uri? = null
    try {
        imagefolder.mkdirs()
        val file = File(imagefolder, "sharedImage" + System.currentTimeMillis() + ".jpeg")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream)
        outputStream.flush()
        outputStream.close()
        uri = FileProvider.getUriForFile(
            context,
            context.resources.getString(R.string.package_name),
            file
        )
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
    }
    return uri
}
