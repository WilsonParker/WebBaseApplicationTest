package com.dev.hare.webbasetemplatemodule.util.view

import android.app.Activity
import android.content.Context
import android.widget.Toast

object BackPressUtil {
    private const val AllowPressTime: Long = 1500
    private var isPressed = false
    private var lastPressTime: Long = 0

    fun onBackPressed(context: Context, callback: () -> Unit) {
        lastPressTime = if (isPressed) lastPressTime else System.currentTimeMillis()
        if (lastPressTime + AllowPressTime > System.currentTimeMillis()) {
            Toast.makeText(context, "한번 더 누르시면 종료합니다", Toast.LENGTH_SHORT).show()
            if (isPressed) {
                callback()
            } else {
                isPressed = true
            }
        } else {
            isPressed = false
            onBackPressed(context, callback)
        }
    }
}