package com.dev.hare.socialloginmodule.activity.basic

import com.dev.hare.socialloginmodule.activity.abstracts.AbstractPaycoActivity
import com.dev.hare.socialloginmodule.util.Logger
import com.toast.android.paycologin.*


class BasicPaycoActivity : AbstractPaycoActivity() {

    override val onLoginListener: OnLoginListener
        get() = object : OnLoginListener {
            override fun onFail(p0: PaycoLoginError?) {
                Logger.log(Logger.LogType.ERROR, "onFail")
            }

            override fun onCancel() {
                Logger.log(Logger.LogType.ERROR, "onCancel")
            }

            override fun onLogin(p0: PaycoLoginExtraResult?) {
                var manager = PaycoLoginManager.getInstance()
                returnMainActivity(manager.accessToken, "", manager.loginId)
            }
        }
    override val onLogoutListener: OnLogoutListener
        get() = object : OnLogoutListener {
            override fun onFail(p0: PaycoLoginError?) {
                Logger.log(Logger.LogType.ERROR, "onFail")
            }

            override fun onLogout() {
                Logger.log(Logger.LogType.ERROR, "onLogout")
            }
        }

    override fun unLink() {
        Logger.log(Logger.LogType.INFO, "unLink")
    }
}
