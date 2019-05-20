package com.dev.hare.webbasetemplatemodule.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.webkit.*
import androidx.annotation.RequiresApi
import com.dev.hare.webbasetemplatemodule.activity.BaseWebActivity
import com.example.user.webviewproject.net.listener.WebViewBaseCommand

abstract class BaseWebView : WebView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    abstract val host: String
    protected abstract val windowActivity: Class<BaseWebActivity>
    private val _currentHostReg = """(${extractUrlLastSlash(host)})?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()

    var javascriptBrideInterface: JavascriptBridgeFrame? = null
        @SuppressLint("JavascriptInterface")
        set(value) {
            field = value
            addJavascriptInterface(value, "android")
        }

    init {
        init()
    }

    @SuppressLint("JavascriptInterface")
    fun init() {
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
            allowFileAccess = true
            allowFileAccessFromFileURLs = true //Maybe you don't need this rule
            allowUniversalAccessFromFileURLs = true
            javaScriptCanOpenWindowsAutomatically = true
            // this.setSupportMultipleWindows(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setRenderPriority(WebSettings.RenderPriority.HIGH)

            userAgentString = "$userAgentString/app_android"
        }


        object : WebViewBaseCommand {
            override fun isCurrentHost(host: String): Boolean = host.matches(_currentHostReg)

            override fun newWindow(url: Uri) {
                if (isCurrentHost(url.toString())) {
                    context.startActivity(Intent(context, windowActivity).apply { data = url })
                } else {
                    newApplication(url)
                }
            }

            override fun newApplication(url: Uri) {
                context.startActivity(Intent(Intent.ACTION_VIEW).apply { data = url })
            }
        }.let {
            /*webViewClient = BaseWebViewClient(it)
            webChromeClient = BaseWebChromeClient(context, it)*/
        }

        loadUrl(host)
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

    private fun extractUrlLastSlash(url: String): String {
        return if (url.takeLast(1) == "/") url.dropLast(1) else url
    }

    fun isCurrentHost(url: String): Boolean {
        return extractUrlLastSlash(url) == extractUrlLastSlash(host)
    }

    interface JavascriptBridgeFrame
}