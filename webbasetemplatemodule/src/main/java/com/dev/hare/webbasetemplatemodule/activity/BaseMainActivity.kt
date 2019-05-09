package com.dev.hare.webbasetemplatemodule.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dev.hare.webbasetemplatemodule.util.AlertUtil
import com.dev.hare.webbasetemplatemodule.util.CommonUtils

abstract class BaseMainActivity : AppCompatActivity() {
    abstract val introClass: Class<out BaseIntroActivity>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (CommonUtils.isNetworkState(applicationContext) === CommonUtils.NetworkState.NOT_CONNECTED) {
            AlertUtil.alert(this, "네트워크 신호가 원활 하지 않습니다\n확인 후 다시 접속 바랍니다")
        } else {
            // 인트로 페이지 추가
            val introIntent = Intent(this, introClass)
            introIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(introIntent)
        }

        onCreateInit(savedInstanceState)
    }

    abstract fun onCreateInit(savedInstanceState: Bundle?)

}
