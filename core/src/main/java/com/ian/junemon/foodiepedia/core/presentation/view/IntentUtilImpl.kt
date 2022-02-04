package com.ian.junemon.foodiepedia.core.presentation.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.RequestManager
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.dagger.qualifier.ApplicationIoScope
import com.ian.junemon.foodiepedia.core.dagger.qualifier.MainDispatcher
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


/**
 * Created by Ian Damping on 06,December,2019
 * Github https://github.com/iandamping
 * Indonesia.
 */
class IntentUtilImpl @Inject constructor(
    @ApplicationIoScope private val scope: CoroutineScope,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    @ActivityContext private val context: Context,
    private val requestManager: RequestManager,
) : IntentUtilHelper {

    override fun intentShareImageAndText(tittle: String?, message: String?, imageUrl: String?) {
        scope.launch {
            try {
                val bitmap = requestManager
                    .asBitmap()
                    .load(requireNotNull(imageUrl) {
                        "picture to share is null"
                    })
                    .submit(512, 512)
                    .get()
                if (getUriImageToShare(bitmap) != null) {
                    withContext(mainDispatcher) {
                        val sharingIntent = Intent(Intent.ACTION_SEND)
                        sharingIntent.type = "image/*"
                        sharingIntent.putExtra(Intent.EXTRA_STREAM, getUriImageToShare(bitmap))
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
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    // Retrieving the url to share
    private fun getUriImageToShare(bitmap: Bitmap): Uri? {
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

    override fun intentShareText(text: String) {
        val shareIntent = ShareCompat.IntentBuilder(context)
            .setText(text)
            .setType("text/plain")
            .createChooserIntent()
            .apply {
                // https://android-developers.googleblog.com/2012/02/share-with-intents.html
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    // If we're on Lollipop, we can open the intent as a document
                    addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                } else {
                    // Else, we will use the old CLEAR_WHEN_TASK_RESET flag
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
                }
            }
        context.startActivity(shareIntent)
    }

    override fun intentOpenWebsite(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        context.startActivity(openURL)
    }


}