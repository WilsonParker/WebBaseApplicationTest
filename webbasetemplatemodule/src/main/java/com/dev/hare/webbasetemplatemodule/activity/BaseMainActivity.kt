package com.dev.hare.webbasetemplatemodule.activity

import android.content.Intent
import android.os.Bundle
import com.dev.hare.webbasetemplatemodule.util.AlertUtil
import com.dev.hare.webbasetemplatemodule.util.CommonUtils

abstract class BaseMainActivity : BaseActivity() {
    abstract val introClass: Class<out BaseIntroActivity>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (CommonUtils.isNetworkState(applicationContext) === CommonUtils.NetworkState.NOT_CONNECTED) {
            AlertUtil.alert(this, "네트워크 신호가 원활 하지 않습니다\n확인 후 다시 접속 바랍니다")
        } else {
            // 인트로 페이지 추가
            val introIntent = Intent(this, introClass)
            introIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(introIntent)
        }

        onCreateInit(savedInstanceState)
    }

}

/*@SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun init(){
        webview.apply {
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

            addJavascriptInterface(this, "push")
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
        }
        webview.settings.apply {
            if (Build.VERSION.SDK_INT >= 21) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                val cookieManager = CookieManager.getInstance()
                cookieManager.setAcceptCookie(true)
                cookieManager.setAcceptThirdPartyCookies(webview, true)
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
            //mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically( true );

            javaScriptCanOpenWindowsAutomatically = true
            this.setSupportMultipleWindows(true)
            layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
            setRenderPriority(WebSettings.RenderPriority.HIGH)
        }

        webview.loadUrl("http://web-dev.enter6.co.kr/")

    }

    *//*
     * @javascript
     *
     * push.login($user_code);
     * *//*
    @JavascriptInterface
    fun login(user_code: String) {
        Logger.log(Logger.LogType.INFO, "usecode : $user_code")
        FCMHttpService.updateTokenWithUserCode(user_code);
    }*/