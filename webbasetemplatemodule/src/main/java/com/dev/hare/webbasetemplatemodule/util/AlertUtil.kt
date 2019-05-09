package com.dev.hare.webbasetemplatemodule.util

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.support.v7.app.AlertDialog

object AlertUtil {

    fun alert(activity: Activity, message: String) {
        val alert = AlertDialog.Builder(activity).apply {
            setMessage(message)
            setCancelable(false)
            setPositiveButton(
                "확인"
            ) { dialog, which ->
                dialog.cancel()
                activity.finish()
            }
        }.create()
        alert.show()
        alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.parseColor("#55B5A4"))
    }
}