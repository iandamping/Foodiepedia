package com.ian.junemon.foodiepedia.dagger.module

import android.content.Context
import android.os.Environment
import com.ian.junemon.foodiepedia.R
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraXFileDirectory
import com.ian.junemon.foodiepedia.core.dagger.qualifier.DefaultCameraFileDirectory
import com.ian.junemon.foodiepedia.util.FoodConstant.FILENAME
import com.ian.junemon.foodiepedia.util.FoodConstant.PHOTO_EXTENSION
import dagger.Module
import dagger.Provides
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Ian Damping on 07,August,2020
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CameraXModule {

    @Provides
    @CameraXFileDirectory
    fun provideCameraXDirectoryFile(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val cameraXDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.applicationContext.filesDir

        return File(
            cameraXDir, "$FILENAME$PHOTO_EXTENSION"
        )
    }


    @Provides
    @DefaultCameraFileDirectory
    fun provideDefaultDirectoryFile(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        val cameraXDir = if (mediaDir != null && mediaDir.exists())
            mediaDir else context.applicationContext.filesDir

        return File(
            cameraXDir, "$FILENAME$PHOTO_EXTENSION"
        )

        // Create an image file name
        // val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        // val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // return File.createTempFile(
        //     "JPEG_${timeStamp}_", /* prefix */
        //     ".jpg", /* suffix */
        //     storageDir /* directory */
        // )

    }
}