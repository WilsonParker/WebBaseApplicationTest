package com.example.user.webviewproject.net.listener

import android.net.Uri

interface WebViewCommand {

    fun isNewWindow(url: Uri): Boolean

    fun load(url: Uri): Boolean

    fun newWindow(url: Uri):Boolean

    fun newApplication(url: Uri):Boolean

}