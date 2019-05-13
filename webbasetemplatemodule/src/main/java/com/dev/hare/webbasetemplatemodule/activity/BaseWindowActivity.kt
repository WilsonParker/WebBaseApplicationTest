package com.dev.hare.webbasetemplatemodule.activity

import android.os.Bundle
import android.view.View
import com.dev.hare.webbasetemplatemodule.R
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseWindowActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_window)
        initWebView()
    }

    private fun initWebView(){
        intent?.data.let {
            webview.loadUrl(it.toString())
        }
    }

}
