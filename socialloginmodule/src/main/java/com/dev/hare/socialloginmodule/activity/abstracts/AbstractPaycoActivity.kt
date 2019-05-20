package com.dev.hare.socialloginmodule.activity.abstracts

import android.content.Intent
import com.toast.android.paycologin.OnLoginListener
import com.toast.android.paycologin.OnLogoutListener
import com.toast.android.paycologin.PaycoLoginManager


abstract class AbstractPaycoActivity : AbstractSocialActivity() {
    companion object {
        const val REQUEST_CODE = 0x0014
    }

    protected abstract val onLoginListener: OnLoginListener
    protected abstract val onLogoutListener: OnLogoutListener

    override fun logIn() {
        PaycoLoginManager.getInstance().login(this, onLoginListener)
    }

    override fun logOut() {
        PaycoLoginManager.getInstance().logout(onLogoutListener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        PaycoLoginManager.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

}
