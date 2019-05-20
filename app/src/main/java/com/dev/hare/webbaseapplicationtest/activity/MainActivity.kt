package com.dev.hare.webbaseapplicationtest.activity

import android.content.Intent
import android.os.Bundle
import com.dev.hare.firebasepushmodule.basic.BasicTokenCallService
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.firebasepushmodule.util.Logger
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractPaycoActivity
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractSocialActivity
import com.dev.hare.socialloginmodule.util.KeyHashManager
import com.dev.hare.webbaseapplicationtest.R
import com.dev.hare.webbaseapplicationtest.util.NicePayUtility
import com.dev.hare.webbaseapplicationtest.web.AndroidBridge
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import com.dev.hare.webbasetemplatemodule.activity.BaseMainActivity
import com.dev.hare.webbasetemplatemodule.util.CommonUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMainActivity() {
    override val introClass: Class<BaseIntroActivity>
        get() = IntroActivity::class.java as Class<BaseIntroActivity>

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
        FirebaseUtil.getToken(object : FirebaseUtil.OnGetTokenSuccessListener {
            override fun onSuccess(token: String) {
                BasicTokenCallService.insertToken(token, CommonUtil.getDeviceUUID(this@MainActivity)) {
                    Logger.log(Logger.LogType.INFO, "token sequence : ${it?.toString()}")
                }
            }
        })
        Logger.log(Logger.LogType.INFO, "keyhash", "" + KeyHashManager.getKeyHash(this))

        webview.javascriptBrideInterface = AndroidBridge(this)
        webview.initWebView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NicePayUtility.REQUEST_CODE) {
            data?.let { data ->
                NicePayUtility.onActivityResult(requestCode, resultCode, data, webview, this)
            }
        // } else if (requestCode == AbstractKakaoActivity.REQUEST_CODE || requestCode == AbstractNaverActivity.REQUEST_CODE || requestCode == AbstractFacebookActivity.REQUEST_CODE) {
        } else if ((requestCode or AbstractSocialActivity.RESULT_CODE) >= AbstractSocialActivity.RESULT_CODE)  {
            if (resultCode == AbstractSocialActivity.RESULT_CODE_SUCCESS) {
                data?.let {
                    var accessToken = data.getStringExtra(AbstractSocialActivity.Key.AccessToken.getValue())
                    var refreshToken = data.getStringExtra(AbstractSocialActivity.Key.RefreshToken.getValue())
                    var id = data.getStringExtra(AbstractSocialActivity.Key.Id.getValue())
                    Logger.log(Logger.LogType.INFO, accessToken, refreshToken, id)
                    when(requestCode){
                        AbstractPaycoActivity.REQUEST_CODE -> {
                        }
                    }
                }
            }
        }
    }

}
