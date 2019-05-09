package com.dev.hare.firebasepushmodule.sample

import android.content.Context
import com.dev.hare.firebasepushmodule.util.Logger
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId

object FCMUtil {

    fun init(context: Context) {
        FirebaseApp.initializeApp(context)
    }

    fun getToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Logger.log(Logger.LogType.INFO, "token = ${it.token}")
            FCMHttpService.insertToken(it.token, "")
        }
    }
}