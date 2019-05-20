package com.dev.hare.webbasetemplatemodule.activity

import android.os.Bundle
import com.dev.hare.webbasetemplatemodule.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_window.*

abstract class BaseWindowActivity : BaseWebActivity() {
    abstract val contentView:Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)
        initWebView()
    }

    private fun initWebView(){
        intent?.data.let {
            webview.loadUrl(it.toString())
        }
    }

}
