package com.example.user.webviewproject.net

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Message
import android.webkit.*
import androidx.annotation.RequiresApi
import com.example.user.webviewproject.net.listener.WebViewBaseCommand
import com.example.user.webviewproject.util.FileChooserManager as FCManager

class BaseWebChromeClient(private val context: Context, private val _webViewBaseCommand: WebViewBaseCommand) :
    WebChromeClient() {

    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {
        var newWebView = WebView(view!!.context).apply {
            webViewClient = object : WebViewClient() {

                @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                    // logger.info("shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean")
                    request?.let { _webViewBaseCommand.newWindow(it.url) }
                    return true
                }

                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    // logger.info("shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean")
                    _webViewBaseCommand.newWindow(Uri.parse(url))
                    return true
                }
            }
        }
        view?.addView(newWebView)
        (resultMsg!!.obj as WebView.WebViewTransport).webView = newWebView
        resultMsg.sendToTarget()
        return true
    }

    // For Android Version 5.0+
    // Ref: https://github.com/GoogleChrome/chromium-webview-samples/blob/master/input-file-example/app/src/main/java/inputfilesample/android/chrome/google/com/inputfilesample/MainFragment.java
    override fun onShowFileChooser(
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        FCManager.apply{
            mFilePathCallback?.onReceiveValue(null)
            mFilePathCallback = filePathCallback
            imageChooser(context as Activity)
        }
        return true
    }
}