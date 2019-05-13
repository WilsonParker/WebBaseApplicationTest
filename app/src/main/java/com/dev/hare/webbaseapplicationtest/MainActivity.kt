package com.dev.hare.webbaseapplicationtest

import android.os.Bundle
import com.dev.hare.firebasepushmodule.util.FirebaseUtil
import com.dev.hare.webbaseapplicationtest.firebase.FCMHttpService
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import com.dev.hare.webbasetemplatemodule.activity.BaseMainActivity

class MainActivity : BaseMainActivity() {
    override val introClass: Class<BaseIntroActivity>
        get() = IntroActivity::class.java as Class<BaseIntroActivity>

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)

        FirebaseUtil.getToken(object : FirebaseUtil.OnGetTokenSuccessListener {
            override fun onSuccess(token: String) {
                FCMHttpService.insertToken(token, "")
            }
        })
    }
}
