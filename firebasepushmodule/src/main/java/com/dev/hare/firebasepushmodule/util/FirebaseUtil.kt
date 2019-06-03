package com.dev.hare.firebasepushmodule.util

import android.content.Context
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.hareutilitymodule.util.file.PreferenceUtil
import com.google.firebase.iid.FirebaseInstanceId

object FirebaseUtil {
    private const val PrefToken = "firebaseToken"
    private const val PrefTokenSequence = "firebaseTokenSequence"

    fun getToken(getTokenSuccessListener: OnGetTokenSuccessListener) {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            Logger.log(Logger.LogType.INFO, "token = ${it.token}")
            getTokenSuccessListener.onSuccess(it.token)
        }
    }

    fun getTokenSequence(context: Context): Int {
        var sequence = PreferenceUtil.getData(context, PrefTokenSequence)
        return if(sequence == "") -1 else Integer.parseInt(sequence)
    }

    fun isTokenRestored(context: Context, token: String): Boolean {
        var prefToken = PreferenceUtil.getData(context, PrefToken)
        return if (prefToken == "" || prefToken != token) {
            PreferenceUtil.setData(context, PrefToken, token)
            PreferenceUtil.setData(context, PrefTokenSequence, "${HttpConstantModel.token_sequence}")
            true
        } else {
            false
        }
    }

    fun resetToken(context: Context) {
        PreferenceUtil.setData(context, PrefToken, "")
        PreferenceUtil.setData(context, PrefTokenSequence, "")
    }


    interface OnGetTokenSuccessListener {
        fun onSuccess(token: String)
    }
}