package com.dev.hare.webbaseapplicationtest.activity

import android.content.Intent
import android.os.Bundle
import com.dev.hare.firebasepushmodule.http.model.HttpConstantModel
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractPaycoActivity
import com.dev.hare.socialloginmodule.activity.abstracts.AbstractSocialActivity
import com.dev.hare.socialloginmodule.util.KeyHashManager
import com.dev.hare.webbaseapplicationtest.R
import com.dev.hare.webbaseapplicationtest.constants.APP_URL
import com.dev.hare.webbaseapplicationtest.util.NicePayUtility
import com.dev.hare.webbaseapplicationtest.web.AndroidBridge
import com.dev.hare.webbasetemplatemodule.activity.BaseMainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.reflect.KClass

class MainActivity : BaseMainActivity<IntroActivity>() {
    override val introClass: KClass<IntroActivity>
        get() = IntroActivity::class

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
        FirebaseUtil.getToken(object : FirebaseUtil.OnGetTokenSuccessListener {
            override fun onSuccess(token: String) {
                if (FirebaseUtil.isTokenRestored(this@MainActivity, token)){
                    /*BasicTokenCallService.insertToken(token, CommonUtil.getDeviceUUID(this@MainActivity)) {
                        Logger.log(Logger.LogType.INFO, "token sequence : ${it?.toString()}")
                    }*/
                } else {
                    HttpConstantModel.token_sequence = FirebaseUtil.getTokenSequence(this@MainActivity)
                }
            }
        })
        Logger.log(Logger.LogType.INFO, "keyhash", "" + KeyHashManager.getKeyHash(this))

        webview.javascriptBrideInterface = AndroidBridge(this)
        webview.host = APP_URL
        webview.initWebView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NicePayUtility.REQUEST_CODE) {
            data?.let {
                NicePayUtility.onActivityResult(requestCode, resultCode, it, webview, this)
            }
            // } else if (requestCode == AbstractKakaoActivity.REQUEST_CODE || requestCode == AbstractNaverActivity.REQUEST_CODE || requestCode == AbstractFacebookActivity.REQUEST_CODE) {
        } else if ((requestCode or AbstractSocialActivity.RESULT_CODE) >= AbstractSocialActivity.RESULT_CODE) {
            if (resultCode == AbstractSocialActivity.RESULT_CODE_SUCCESS) {
                data?.let {
                    var accessToken = data.getStringExtra(AbstractSocialActivity.Key.AccessToken.getValue())
                    var refreshToken = data.getStringExtra(AbstractSocialActivity.Key.RefreshToken.getValue())
                    var id = data.getStringExtra(AbstractSocialActivity.Key.Id.getValue())
                    Logger.log(Logger.LogType.INFO, accessToken, refreshToken, id)
                    when (requestCode) {
                        AbstractPaycoActivity.REQUEST_CODE -> {
                        }
                    }
                }
            }
        }
    }

}
