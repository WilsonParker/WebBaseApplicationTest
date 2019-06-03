package com.dev.hare.webbasetemplatemodule.web

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.dev.hare.webbasetemplatemodule.util.UrlUtil
import com.example.user.webviewproject.net.listener.WebViewCommand
import kotlin.reflect.KClass

open class BaseWebViewCommand<activity : Activity>(
    private val context: Context,
    val host: String,
    private val activityCls: KClass<activity>
) : WebViewCommand {

    override fun isNewWindow(url: Uri): Boolean {
        return !UrlUtil.isCurrentDomain(host, url.toString())
    }

    override fun load(url: Uri): Boolean {
        return if (!UrlUtil.isCurrentHost(host, url.toString()) || isNewWindow(url)) newWindow(url) else newApplication(url)
    }

    override fun newWindow(url: Uri): Boolean {
        context.startActivity(Intent(context, activityCls.java).apply { data = url })
        return true
    }

    override fun newApplication(url: Uri): Boolean {
        context.startActivity(Intent(Intent.ACTION_VIEW).apply { data = url })
        return true
    }
}