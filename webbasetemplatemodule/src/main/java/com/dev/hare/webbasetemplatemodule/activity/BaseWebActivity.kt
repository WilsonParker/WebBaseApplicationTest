package com.dev.hare.webbasetemplatemodule.activity

import android.content.Intent
import android.os.Build
import com.dev.hare.webbasetemplatemodule.util.UrlUtil.isCurrentDomain
import com.dev.hare.webbasetemplatemodule.util.view.BackPressUtil
import kotlinx.android.synthetic.main.activity_window.*
import com.example.user.webviewproject.util.FileChooserManager as FCManager
import com.example.user.webviewproject.util.FileChooserManager2 as FCManager2
import com.example.user.webviewproject.util.FileChooserManagerRenewer as FCManager3

abstract class BaseWebActivity : BaseActivity() {

    override fun onBackPressed() {
        webview.run {
            if (isCurrentDomain(url, host) && canGoBack()) {
                goBack()
            } else {
                BackPressUtil.onBackPressed(this@BaseWebActivity) { super.onBackPressed() }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            FCManager3.onActivityResult(requestCode, resultCode, data, this) { requestCode: Int, resultCode: Int, data: Intent? ->
                super.onActivityResult(requestCode, resultCode, data)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

}