package com.dev.hare.daisowebviewtest.web

import android.app.Activity
import android.content.Context
import android.webkit.WebView
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewCommand
import kotlin.reflect.KClass


class CustomWebViewCommand<activity : Activity>(
    private val context: Context,
    host: String,
    activityCls: KClass<activity>,
    private val webView: WebView
) : BaseWebViewCommand<activity>(
    context,
    host,
    activityCls
) {

}