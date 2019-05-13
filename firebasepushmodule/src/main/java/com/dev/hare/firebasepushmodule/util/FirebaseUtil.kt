package com.dev.hare.firebasepushmodule.util

import com.google.firebase.iid.FirebaseInstanceId

object FirebaseUtil {

    fun getToken(getTokenSuccessListener: OnGetTokenSuccessListener) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Logger.log(Logger.LogType.INFO, "token = ${it.token}")
            getTokenSuccessListener.onSuccess(it.token)
        }
    }

    interface OnGetTokenSuccessListener {
        fun onSuccess(token: String)
    }
}