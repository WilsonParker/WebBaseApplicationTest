package com.dev.hare.webbaseapplicationtest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.hare.firebasepushmodule.sample.FCMUtil
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import com.dev.hare.webbasetemplatemodule.activity.BaseMainActivity

class MainActivity : BaseMainActivity() {
    override val introClass: Class<BaseIntroActivity>
        get() = IntroActivity::class.java as Class<BaseIntroActivity>

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        FCMUtil.init(this)
        FCMUtil.getToken()
    }

}
