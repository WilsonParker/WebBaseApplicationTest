package com.example.user.webviewproject.net

import android.net.Uri
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import com.example.user.webviewproject.net.listener.WebViewBaseCommand
import java.net.URL
import java.util.logging.Logger

class BaseWebViewClient(private val _webViewBaseCommand: WebViewBaseCommand) : WebViewClient() {

    private fun newApplicationIfOtherHost(url: String): Boolean {
        return if (!_webViewBaseCommand.isCurrentHost(url)) {
            _webViewBaseCommand.newApplication(Uri.parse(url))
            true
        } else
            false
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        request?.url?.let { return newApplicationIfOtherHost(it.toString()) }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        url?.let { return newApplicationIfOtherHost(url) }
        return super.shouldOverrideUrlLoading(view, url)
    }

    private fun handleRequest(urlString: String): WebResourceResponse {
        var url = URL(urlString)
        var connection = url.openConnection().apply {
            setRequestProperty("User-Agent","")
            setRequestProperty("method","get")
            doInput = true
        }
        connection.connect()
        var inputStream = connection.getInputStream()
        return WebResourceResponse("text/json", "utf-8", inputStream)
    }
}