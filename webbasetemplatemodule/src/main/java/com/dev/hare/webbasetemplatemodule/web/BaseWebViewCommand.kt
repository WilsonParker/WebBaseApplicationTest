package com.dev.hare.webbasetemplatemodule.web

import android.app.Activity
import android.content.ActivityNotFoundException
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
    private val INTENT_PROTOCOL_START = "intent:";
    private val INTENT_PROTOCOL_INTENT = "#Intent;";
    private val INTENT_PROTOCOL_END = ";end;";
    private val GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";

    override fun isNewWindow(url: Uri): Boolean {
        return !UrlUtil.isCurrentHost(host, url.toString()) || !UrlUtil.isCurrentDomain(host, url.toString())
    }

    override fun isNewApplication(url: Uri): Boolean {
        return url.toString().run {
            startsWith(INTENT_PROTOCOL_START) || startsWith(GOOGLE_PLAY_STORE_PREFIX)
        }
    }

    override fun load(url: Uri): Boolean {
        if (isNewApplication(url))
            return newApplication(url)
        return if (isNewWindow(url))
            newWindow(url)
        else
            newApplication(url)
    }

    override fun newWindow(url: Uri): Boolean {
        context.startActivity(Intent(context, activityCls.java).apply { data = url })
        return true
    }

    override fun newApplication(url: Uri): Boolean {
        return url.toString().run {
            if (startsWith(INTENT_PROTOCOL_START)) {
                val customUrlStartIndex = INTENT_PROTOCOL_START.length
                val customUrlEndIndex = indexOf(INTENT_PROTOCOL_INTENT)
                if (customUrlEndIndex < 0) {
                    false
                } else {
                    val customUrl = substring(customUrlStartIndex, customUrlEndIndex)
                    try {
                        context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)))
                    } catch (e: ActivityNotFoundException) {
                        val packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length
                        val packageEndIndex = indexOf(INTENT_PROTOCOL_END)

                        val packageName =
                            substring(packageStartIndex, if (packageEndIndex < 0) length else packageEndIndex)
                        context.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)
                            )
                        )
                    }
                    true
                }
            } else {
                false
            }
        }
    }

}