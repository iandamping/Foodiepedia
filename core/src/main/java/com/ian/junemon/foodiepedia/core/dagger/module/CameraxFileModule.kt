package com.ian.junemon.foodiepedia.core.dagger.module

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import com.ian.junemon.foodiepedia.core.R
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxContentValues
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxOutputDirectory
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxOutputOptions
import com.ian.junemon.foodiepedia.core.dagger.qualifier.CameraxPhotoFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
@InstallIn(SingletonComponent::class)
object CameraxFileModule {

    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"

    @Provides
    @Singleton
    @CameraxContentValues
    fun provideContentValues(): ContentValues {
        // Create time stamped name and MediaStore entry.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())

        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }
    }

    @Provides
    @Singleton
    @CameraxOutputOptions
    fun provideOutputOption(
        @ApplicationContext context: Context,
        @CameraxContentValues contentValues: ContentValues
    ): ImageCapture.OutputFileOptions {
        // Create output options object which contains file + metadata
        return ImageCapture.OutputFileOptions
            .Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
    }
}