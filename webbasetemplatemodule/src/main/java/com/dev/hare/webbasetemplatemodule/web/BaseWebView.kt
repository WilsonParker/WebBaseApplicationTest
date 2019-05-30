package com.dev.hare.webbasetemplatemodule.web

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.RequiresApi
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import com.example.user.webviewproject.net.BaseWebChromeClient
import com.example.user.webviewproject.net.BaseWebViewClient

abstract class BaseWebView<Activity : BaseWindowActivity>: WebView, Cloneable{
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    var webViewCommand: BaseWebViewCommand<Activity>? = null
        set(value) {
            field = value
            webViewClient = BaseWebViewClient(value)
            webChromeClient = BaseWebChromeClient(context, value)
        }
    abstract var host:String

    var javascriptBrideInterface: JavascriptBridgeFrame? = null
        @SuppressLint("JavascriptInterface")
        set(value) {
            field = value
            addJavascriptInterface(value, "android")
        }

    init {
        init()
    }

    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    private fun init() {
        scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        //웹뷰 속도개선
        if (Build.VERSION.SDK_INT >= 19) {
            setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        clearHistory()
        clearFormData()
        clearCache(true)
        setWebContentsDebuggingEnabled(true)
        clearView()

        settings.apply {
            if (Build.VERSION.SDK_INT >= 21) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(this@BaseWebView, true)
            }

            useWideViewPort = true
            this.setSupportZoom(true)
            builtInZoomControls = true
            //줌컨트롤러 안보이게 처리
            displayZoomControls = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            setSavePassword(false)
            setAppCacheEnabled(true)
            domStorageEnabled = true
            javaScriptEnabled = true
            setSupportMultipleWindows(true)
            allowFileAccess = true
            allowFileAccessFromFileURLs = true //Maybe you don't need this rule
            allowUniversalAccessFromFileURLs = true
            allowContentAccess = true
            javaScriptCanOpenWindowsAutomatically = true
            // this.setSupportMultipleWindows(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            textZoom = 100
            userAgentString = "$userAgentString/app_android"

            pluginState = WebSettings.PluginState.OFF
            loadWithOverviewMode = true
            supportZoom()
        }
        webViewClient = BaseWebViewClient(webViewCommand)
        webChromeClient = BaseWebChromeClient(context, webViewCommand)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setMixedContentAllowed(allowed: Boolean) {
        settings.mixedContentMode =
            if (allowed) WebSettings.MIXED_CONTENT_ALWAYS_ALLOW else WebSettings.MIXED_CONTENT_NEVER_ALLOW
    }

    fun setThirdPartyCookiesEnabled(enabled: Boolean) {
        CookieManager.getInstance().setAcceptCookie(enabled)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, enabled)
        }
    }

    interface JavascriptBridgeFrame
}