package com.example.user.webviewproject.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.ValueCallback
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

object FileChooserManager {
    const val REQUEST_CODE = 0x0001

    private const val TYPE_IMAGE = "image/*"
    var mUploadMessage: ValueCallback<Uri>? = null
    var mFilePathCallback: ValueCallback<Array<Uri>>? = null
    var mCameraPhotoPath: String? = null

    fun imageChooser(activity: Activity) {
        var takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.run {
            if (resolveActivity(activity.packageManager) != null) {
                var photoFile = createImageFile()
                if (photoFile != null) {
                    mCameraPhotoPath = "file:$photoFile.absolutePath"
                    putExtra("PhotoPath", mCameraPhotoPath)
                    putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile))
                } else {
                    null
                }
            }
        }

        activity.startActivityForResult(
            Intent(ACTION_CHOOSER).apply {
                putExtra(EXTRA_INTENT, Intent(ACTION_GET_CONTENT).apply {
                    addCategory(CATEGORY_OPENABLE)
                    type = TYPE_IMAGE
                })
                putExtra(EXTRA_TITLE, "Image Chooser")
                putExtra(
                    EXTRA_INITIAL_INTENTS,
                    if (takePictureIntent != null) arrayOf(takePictureIntent) else arrayOfNulls<Intent>(0)
                )
            }, REQUEST_CODE
        )
    }

    private fun createImageFile(): File {
        return File.createTempFile(
            "JPEG_${SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())}_"   // prefix
            , ".jpg"                                                                 // suffix
            , Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) // directory
        )
    }

    fun getResultUri(context: Context, data: Intent?): Uri {
        return if (data == null || TextUtils.isEmpty(data.dataString)) {
            Uri.parse(mCameraPhotoPath)
        } else {
            Uri.parse(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    data.dataString
                } else {
                    "file:" + RealPathUtil.getRealPath(context, data.data)
                }
            )
        }
    }
}