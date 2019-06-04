package com.dev.hare.webbaseapplicationtest.web

import android.app.Activity
import android.content.Intent
import android.webkit.JavascriptInterface
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.socialloginmodule.activity.abstracts.*
import com.dev.hare.socialloginmodule.activity.basic.BasicFacebookActivity
import com.dev.hare.socialloginmodule.activity.basic.BasicKakaoActivity
import com.dev.hare.socialloginmodule.activity.basic.BasicNaverActivity
import com.dev.hare.socialloginmodule.activity.basic.BasicPaycoActivity
import com.dev.hare.webbaseapplicationtest.push.BasicTokenCallService
import com.dev.hare.webbasetemplatemodule.web.BaseWebView

class AndroidBridge(val activity: Activity) : BaseWebView.JavascriptBridgeFrame {

    @JavascriptInterface
    fun logIn(user_code: String) {
        Logger.log(Logger.LogType.INFO, "usercode : $user_code")
        Logger.log(Logger.LogType.INFO, "token_sequence : ${HttpConstantModel.token_sequence}")
        BasicTokenCallService.updateTokenWithUserCode(user_code) { result ->
            Logger.log(Logger.LogType.INFO, "updateTokenWithUserCode : ${result.toString()}")
        }
        if (HttpConstantModel.token_sequence == -1)
            FirebaseUtil.resetToken()
    }

    @JavascriptInterface
    fun logOut() {
        Logger.log(Logger.LogType.INFO, "logout")
    }

    @JavascriptInterface
    fun setPushAgreement(agreement: Boolean) {
        BasicTokenCallService.updateTokenWithAgreement(agreement) { result ->
            Logger.log(Logger.LogType.INFO, "setPushAgreement : ${result.toString()}")
        }
    }

    @JavascriptInterface
    fun kakaoLogin() {
        Logger.log(Logger.LogType.INFO, "kakaoLogin")
        val it = Intent(activity, BasicKakaoActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractKakaoActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun naverLogin() {
        Logger.log(Logger.LogType.INFO, "naverLogin")
        val it = Intent(activity, BasicNaverActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractNaverActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun facebookLogin() {
        Logger.log(Logger.LogType.INFO, "facebookLogin")
        val it = Intent(activity, BasicFacebookActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractFacebookActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun paycoLogin() {
        Logger.log(Logger.LogType.INFO, "paycoLogin")
        val it = Intent(activity, BasicPaycoActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractPaycoActivity.REQUEST_CODE)
    }
}