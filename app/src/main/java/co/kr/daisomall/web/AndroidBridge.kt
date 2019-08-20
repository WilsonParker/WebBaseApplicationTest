package co.kr.daisomall.web

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.webkit.JavascriptInterface
import com.dev.hare.apputilitymodule.util.Logger
import com.dev.hare.apputilitymodule.util.VersionChecker
import co.kr.daisomall.activity.MainWithIntroActivity
import co.kr.daisomall.activity.MainWithIntroActivity.Companion.mainWebView
import co.kr.daisomall.activity.WindowActivity
import co.kr.daisomall.social.BasicFacebookActivity
import co.kr.daisomall.social.BasicKakaoActivity
import co.kr.daisomall.social.BasicNaverActivity
import co.kr.daisomall.social.BasicPaycoActivity
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.socialloginmodule.activity.abstracts.*
import com.dev.hare.webbasetemplatemodule.util.CommonUtil
import com.dev.hare.webbasetemplatemodule.web.BaseWebView
import com.dev.hare.webbasetemplatemodule.web.BaseWebViewClient
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

class AndroidBridge(val activity: Activity, private val webView: BaseWebView<WindowActivity>) :
    BaseWebView.JavascriptBridgeFrame {

    @JavascriptInterface
    fun logIn(user_code: String) {
        Logger.log(Logger.LogType.INFO, "usercode : $user_code")
        Logger.log(Logger.LogType.INFO, "token_sequence : ${HttpConstantModel.token_sequence}")
        if (HttpConstantModel.token_sequence == -1)
            FirebaseUtil.resetToken()
    }

    @JavascriptInterface
    fun logOut() {
        Logger.log(Logger.LogType.INFO, "logout")
    }

    @JavascriptInterface
    fun requestPermission() {
        val permissionListener = object : PermissionListener {
            override fun onPermissionGranted() {
                // Toast.makeText(activity, "Permission Granted", Toast.LENGTH_SHORT).show()
            }

            override fun onPermissionDenied(deniedPermissions: List<String>) {
                // Toast.makeText(activity, "Permission Denied\n$deniedPermissions", Toast.LENGTH_SHORT).show()
            }
        }

        TedPermission.with(activity)
            .setPermissionListener(permissionListener)
            // .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
            .setPermissions(
                Manifest.permission.READ_CONTACTS
                , Manifest.permission.CALL_PHONE
                , Manifest.permission.CAMERA
                , Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.ACCESS_FINE_LOCATION
                , Manifest.permission.FOREGROUND_SERVICE
                , Manifest.permission.INTERNET
            )
            .check()
    }

    @JavascriptInterface
    fun setPushAgreement(agreement: Boolean) {
        Logger.log(Logger.LogType.INFO, "setPushAgreement : $agreement")
        /*BasicTokenCallService.updateTokenWithAgreement(agreement) { result ->
            Logger.log(Logger.LogType.INFO, "setPushAgreement : ${result.toString()}")
        }*/

    }

    @JavascriptInterface
    fun kakaoLogin() {
        val it = Intent(activity, BasicKakaoActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractKakaoActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun naverLogin() {
        val it = Intent(activity, BasicNaverActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractNaverActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun facebookLogin() {
        val it = Intent(activity, BasicFacebookActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractFacebookActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun paycoLogin() {
        val it = Intent(activity, BasicPaycoActivity::class.java)
        it.putExtra(AbstractSocialActivity.ACTION_TYPE, AbstractSocialActivity.ActionType.LogIn)
        activity.startActivityForResult(it, AbstractPaycoActivity.REQUEST_CODE)
    }

    @JavascriptInterface
    fun activityFinish() {
        Logger.log(Logger.LogType.INFO, "Finish")
        if (activity !is MainWithIntroActivity)
            activity.finish()
    }

    @JavascriptInterface
    fun finishAndHome() {
        Logger.log(Logger.LogType.INFO, "FinishAndHome")
        webView?.post {
            webView.loadUrl("javascript:mm.loading.show()")
        }
        mainWebView?.post {
            mainWebView?.customWebViewClient?.addOnPageFinishCallback(object : BaseWebViewClient.OnPageFinishListener {
                override fun onPageFinish() {
                    activityFinish()
                }
            })
            mainWebView?.home()
        }
    }

    @JavascriptInterface
    fun finish() {
        activity.finish()
    }

    @JavascriptInterface
    fun requestData(data:String = "", scriptName:String = "responseData") {
        Logger.log(Logger.LogType.INFO, "requestData")
        var data = when(data) {
            "token" -> HttpConstantModel.token
            "deviceId" -> CommonUtil.getDeviceUUID(webView.context)
            "appVersion" -> VersionChecker.getVersionInfo(activity)
            "marketVersion" -> VersionChecker.getMarketVersion(activity)
            else -> ""
        }
        webView?.post {
            webView.loadUrl("javascript:$scriptName('$data')")
        }
    }

    // DAISO BRIDGE
    @JavascriptInterface
    fun send_auto_login() {
        webView?.post {
            webView.loadUrl("javascript:send_auto_login('${HttpConstantModel.token}')")
        }
    }

    @JavascriptInterface
    fun requestToken(scriptName:String = "responseToken") {
        Logger.log(Logger.LogType.INFO, "requestToken")
        webView?.post {
            webView.loadUrl("javascript:$scriptName('${HttpConstantModel.token}')")
        }
    }

    @JavascriptInterface
    fun requestDeviceId(scriptName:String = "responseDeviceId") {
        Logger.log(Logger.LogType.INFO, "requestDeviceId")
        webView?.post {
            webView.loadUrl("javascript:$scriptName('${CommonUtil.getDeviceUUID(webView.context)}')")
        }
    }

    @JavascriptInterface
    fun requestAppVersion() {
        webView?.post {
            webView.loadUrl("javascript:responseAppVersion('${VersionChecker.getVersionInfo(activity)}', '${VersionChecker.getMarketVersion(activity)}')")
        }
    }

    @JavascriptInterface
    fun showPopUp() {
        Logger.log(Logger.LogType.INFO, "showPopUp")
        webView?.post {
            webView.loadUrl("javascript:showPopup()")
        }
    }
}