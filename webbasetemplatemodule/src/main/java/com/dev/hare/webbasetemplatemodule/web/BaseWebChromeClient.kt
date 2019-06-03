package com.example.user.webviewproject.net

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Message
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import com.dev.hare.webbasetemplatemodule.web.BaseWebView
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewCommand
import com.example.user.webviewproject.util.FileChooserManager as FCManager
import com.example.user.webviewproject.util.FileChooserManager2 as FCManager2
import com.example.user.webviewproject.util.FileChooserManagerRenewer as FCManager3


open class BaseWebChromeClient<activity : BaseWindowActivity>(
    private val context: Context,
    private val webViewBaseCommand: BaseWebViewCommand<activity>?
) :
    WebChromeClient() {

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        view?.settings?.apply {
            domStorageEnabled = true
            javaScriptEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }

        var newWebView = object : BaseWebView<activity>(context) {
            override var host: String = ""
        }.apply {
            this.webViewCommand = webViewBaseCommand
//            this.webViewClient = BaseWebViewClient(webViewBaseCommand)
//            this.webChromeClient= this@BaseWebChromeClient
        }

        view?.removeAllViews()
        view?.webChromeClient = this
        view?.addView(newWebView)
        (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
        resultMsg.sendToTarget()
        return true
    }

    override fun onCloseWindow(window: WebView?) {
        Logger.log(Logger.LogType.INFO, "onCloseWindow")
        (context as Activity).finish()
    }

    // For Android Version 5.0+
    // Ref: https://github.com/GoogleChrome/chromium-webview-samples/blob/master/input-file-example/app/src/main/java/inputfilesample/android/chrome/google/com/inputfilesample/MainFragment.java
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams
    ): Boolean {
        return FCManager3.onShowFileChooser(webView, filePathCallback, fileChooserParams, context as Activity)
    }


}