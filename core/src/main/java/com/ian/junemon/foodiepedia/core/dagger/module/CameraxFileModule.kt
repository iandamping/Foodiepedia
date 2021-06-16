package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.Context
import androidx.camera.core.ImageCapture
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxOutputDirectory
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxOutputOptions
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxPhotoFile
import dagger.Module
import dagger.Provides
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

/**
 * Created by Ian Damping on 28,May,2021
 * Github https://github.com/iandamping
 * Indonesia.
 */
@Module
object CameraxFileModule {

    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    @Provides
    @Singleton
    @CameraxOutputDirectory
    fun provideOutputDirectory(context: Context): File {
        val mediaDir = context.externalMediaDirs.firstOrNull()?.let {
            File(it, context.resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else context.filesDir
    }

    @Provides
    @Singleton
    @CameraxPhotoFile
    fun providePhotoFile(@CameraxOutputDirectory outputDirectory: File): File {
        return File(
            outputDirectory,
            SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(System.currentTimeMillis()) + ".jpg"
        )
    }

    @Provides
    @Singleton
    @CameraxOutputOptions
    fun provideOutputOption(@CameraxPhotoFile photoFile: File): ImageCapture.OutputFileOptions {
        return ImageCapture.OutputFileOptions.Builder(photoFile).build()
    }
}