package com.dev.hare.webbaseapplicationtest.web

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.webkit.WebView
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewCommand
import kotlin.reflect.KClass


class CustomWebViewCommand<activity : Activity>(
    val context: Context,
    host: String,
    activityCls: KClass<activity>,
    private val webView: WebView
) : BaseWebViewCommand<activity>(
    context,
    host,
    activityCls
) {

    private val localNewWindow = arrayOf("product/detail")

    override fun isNewWindow(url: Uri): Boolean {
        // 내부 WebView 로 실행합니다
        /*return if(UrlUtil.isCurrentDomain(APP_URL, host)){
            !UrlUtil.isCurrentHost(host, url.toString())
        } else {
            !UrlUtil.isCurrentDomain(host, url.toString(), false)
        }*/
        return isLocalNewWindow(url.toString())
    }

    override fun newApplication(url: Uri): Boolean {
        return if(isLocalNewWindow(url.toString()))
            newWindow(url)
        else
            super.newApplication(url)
    }

    /**
     * 내부 url 중 WindowActivity 로 띄울지 결정합니다
     *
     * @param
     * @return
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    private fun isLocalNewWindow(url: String) : Boolean{
        localNewWindow.forEach { local ->
            if(url.contains(local)){
                return true
            }
        }
        return false
    }

}