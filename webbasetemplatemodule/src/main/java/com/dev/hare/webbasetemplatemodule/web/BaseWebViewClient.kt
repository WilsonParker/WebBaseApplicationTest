package com.example.user.webviewproject.net

import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.dev.hare.webbasetemplatemodule.activity.BaseWindowActivity
import com.dev.hare.webbasetemplatemodule.util.UrlUtil
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewCommand
import java.net.URL

open class BaseWebViewClient<Activity : BaseWindowActivity>(private val _webViewBaseCommand: BaseWebViewCommand<Activity>?) :
    WebViewClient() {

    private fun newApplicationIfOtherHost(url: String): Boolean {
        var result = false
        _webViewBaseCommand?.let {
            result = if (UrlUtil.isCurrentHost(_webViewBaseCommand.host, url)) {
                _webViewBaseCommand.newApplication(Uri.parse(url))
                true
            } else
                false
        }
        return result
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        /*var result = true
        request?.let {
            _webViewBaseCommand?.let {
                result = it.load(request.url)
            }
        }
        return if (!result)
            super.shouldOverrideUrlLoading(view, request)
        else
            result*/
        return shouldOverrideUrlLoading(view, request?.url.toString())
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        var result = true
        url?.let {
            _webViewBaseCommand?.let {
                result = it.load(Uri.parse(url))
            }
        }
        return if (!result)
            super.shouldOverrideUrlLoading(view, url)
        else
            result
    }

    private fun handleRequest(urlString: String): WebResourceResponse {
        var url = URL(urlString)
        var connection = url.openConnection().apply {
            setRequestProperty("User-Agent", "")
            setRequestProperty("method", "get")
            doInput = true
        }
        connection.connect()
        var inputStream = connection.getInputStream()
        return WebResourceResponse("text/json", "utf-8", inputStream)
    }

}