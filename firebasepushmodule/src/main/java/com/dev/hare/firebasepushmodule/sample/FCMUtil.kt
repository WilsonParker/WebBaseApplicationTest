package com.dev.hare.firebasepushmodule.sample

import com.dev.hare.firebasepushmodule.sample.FCMHttpService
import com.dev.hare.firebasepushmodule.util.Logger
import com.google.firebase.iid.FirebaseInstanceId

object FCMUtil {

    fun getToken(){
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Logger.log(Logger.LogType.INFO, "token = ${it.token}")
            FCMHttpService.insertToken(it.token, "")
        }
    }
}