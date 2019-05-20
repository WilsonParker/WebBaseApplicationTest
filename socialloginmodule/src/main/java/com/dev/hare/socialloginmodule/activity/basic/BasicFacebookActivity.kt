package com.dev.hare.socialloginmodule.activity.basic

import com.dev.hare.socialloginmodule.activity.abstracts.AbstractFacebookActivity
import com.dev.hare.socialloginmodule.util.Logger
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult


class BasicFacebookActivity : AbstractFacebookActivity() {
    override val loginCallback: FacebookCallback<LoginResult>
        get() = object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val accessToken = result?.accessToken
                Logger.log(Logger.LogType.INFO, "onSuccess")

                Logger.log(Logger.LogType.INFO, "${accessToken?.applicationId}")
                Logger.log(Logger.LogType.INFO, "${accessToken?.token}")
                returnMainActivity(accessToken?.token, "", accessToken?.applicationId)
            }

            override fun onCancel() {
                Logger.log(Logger.LogType.INFO, "onCancel")
            }

            override fun onError(error: FacebookException?) {
                Logger.log(Logger.LogType.ERROR, "FacebookException ${error?.message}")
                Logger.log(Logger.LogType.ERROR, "FacebookException ${error?.stackTrace}")
            }
        }

    override fun unLink() {
        Logger.log(Logger.LogType.INFO, "unLink")
    }

}
