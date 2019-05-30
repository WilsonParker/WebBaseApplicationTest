package com.dev.hare.webbaseapplicationtest.activity

import android.os.Bundle
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.webbaseapplicationtest.R
import com.dev.hare.webbaseapplicationtest.web.CustomWebView
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import kotlinx.android.synthetic.main.activity_window.*

class WindowActivity : BaseWindowActivity() {
    override val webView: CustomWebView
        get() = window_webview

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_window)
        Logger.log(Logger.LogType.INFO, "WindowActivity onCreateInit")
        webView.host = getUrl()
        webView.initWebView(this)
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
    }

}
