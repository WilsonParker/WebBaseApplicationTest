package com.dev.hare.webbaseapplicationtest.web

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.webkit.WebView
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.webbaseapplicationtest.constants.APP_URL
import com.dev.hare.webbasetemplatemodule.util.UrlUtil
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

    override fun isNewWindow(url: Uri): Boolean {
        return if(UrlUtil.isCurrentDomain(APP_URL, host)){
            UrlUtil.isCurrentHost(url.toString(), "mstore.enter6.co.kr")
        } else !UrlUtil.isCurrentDomain(host, url.toString())
    }

    override fun newWindow(url: Uri): Boolean {
        Logger.log(Logger.LogType.INFO, "newWindow $url")
        /*when {
            url.toString().startsWith("intent:") -> {
                // 출처@ https@ //gogorchg.tistory.com/entry/Android-WebView-상에서-Intent-Uri-실행 [항상 초심으로]
                try {
                    val intent = Intent.parseUri(url.toString(), Intent.URI_INTENT_SCHEME)
                    val existPackage = context.packageManager.getLaunchIntentForPackage(intent.getPackage())
                    if (existPackage != null) {
                        context.startActivity(intent)
                    } else {
                        val marketIntent = Intent(Intent.ACTION_VIEW)
                        marketIntent.data = Uri.parse("market://details?id=" + intent.getPackage()!!)
                        context.startActivity(marketIntent)
                    }
                    return true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }*/
        return super.newWindow(url)
    }

    override fun newApplication(url: Uri): Boolean {
        Logger.log(Logger.LogType.INFO, "url : $url")
        val strUrl = url.toString()
        when{
            strUrl.contains("product/detail")->{
                return newWindow(url)
            }
        }
        return super.newApplication(url)
    }

}