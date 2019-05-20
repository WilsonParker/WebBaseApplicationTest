package com.dev.hare.socialloginmodule.activity.basic

import android.os.AsyncTask
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractNaverActivity
import com.dev.hare.socialloginmodule.util.Logger
import com.nhn.android.naverlogin.OAuthLogin


class BasicNaverActivity : AbstractNaverActivity() {
    override fun unLink() {
        DeleteTokenTask().execute()
    }

    private inner class DeleteTokenTask : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            val isSuccessDeleteToken = mOAuthLoginInstance.logoutAndDeleteToken(baseContext)
            if (!isSuccessDeleteToken) {
                Logger.log(Logger.LogType.ERROR, "errorCode: " + mOAuthLoginInstance.getLastErrorCode(baseContext))
                Logger.log(Logger.LogType.ERROR, "errorDesc: " + mOAuthLoginInstance.getLastErrorDesc(baseContext))
            }
            return null
        }

        override fun onPostExecute(v: Void) {
            Logger.log(Logger.LogType.ERROR, "onPostExecute")
        }
    }

    private inner class RefreshTokenTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void): String {
            return OAuthLogin.getInstance().refreshAccessToken(baseContext)
        }

        override fun onPostExecute(res: String) {
            Logger.log(Logger.LogType.ERROR, "onPostExecute")
        }
    }

}