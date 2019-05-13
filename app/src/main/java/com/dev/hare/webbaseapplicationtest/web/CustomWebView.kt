package com.dev.hare.webbaseapplicationtest.web

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import com.dev.hare.firebasepushmodule.example.ExampleHttpService
import com.dev.hare.firebasepushmodule.util.Logger
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import com.dev.hare.webbasetemplatemodule.web.BaseWebView

class CustomWebView : BaseWebView {
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override val host: String
        get() = "http://mobile-enter6.dv9163.kro.kr:8001/"
    override val windowActivity: Class<BaseWindowActivity>
        get() = BaseWindowActivity::class.java
    override val androidBridge: JavascriptBridgeFrame
        get() = AndroidBridge()

    class AndroidBridge : JavascriptBridgeFrame {

        @JavascriptInterface
        fun login(user_code: String) {
            Logger.log(Logger.LogType.INFO, "usecode : $user_code")
            ExampleHttpService.updateTokenWithUserCode(user_code)
        }
    }
}