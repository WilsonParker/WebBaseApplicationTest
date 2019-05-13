package com.example.user.webviewproject.net.listener

import android.net.Uri

interface WebViewBaseCommand {

    fun isCurrentHost(host: String): Boolean

    fun newWindow(url: Uri)

    fun newApplication(url: Uri)

}