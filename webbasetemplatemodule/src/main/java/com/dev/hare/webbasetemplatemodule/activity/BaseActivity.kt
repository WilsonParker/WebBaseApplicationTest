package com.dev.hare.webbasetemplatemodule.activity

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.user.webviewproject.util.FileChooserManager
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity : AppCompatActivity() {
    override fun onBackPressed() {
        webview.run {
            if (canGoBack()) goBack() else super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> when (requestCode) {
                FileChooserManager.REQUEST_CODE -> {
                    FileChooserManager.run {
                        if (requestCode === REQUEST_CODE && resultCode === RESULT_OK) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                if (mFilePathCallback == null) {
                                    super.onActivityResult(requestCode, resultCode, data)
                                    return
                                }
                                val results = arrayOf(getResultUri(this@BaseActivity, data))
                                mFilePathCallback?.onReceiveValue(results)
                                mFilePathCallback = null
                            } else {
                                if (mUploadMessage == null) {
                                    super.onActivityResult(requestCode, resultCode, data)
                                    return
                                }
                                val result = getResultUri(this@BaseActivity, data)

                                // Logger.log(Logger.LogType.INFO,"imageChooser : $result")
                                mUploadMessage?.onReceiveValue(result)
                                mUploadMessage = null
                            }
                        } else {
                            mFilePathCallback?.onReceiveValue(null)
                            mUploadMessage?.onReceiveValue(null)
                            mFilePathCallback = null
                            mUploadMessage = null
                            super.onActivityResult(requestCode, resultCode, data)
                        }
                    }
                }
            }
        }
    }

    abstract fun onCreateInit(savedInstanceState: Bundle?)

}