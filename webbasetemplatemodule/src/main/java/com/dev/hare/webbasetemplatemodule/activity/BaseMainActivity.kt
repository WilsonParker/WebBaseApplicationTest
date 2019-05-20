package com.dev.hare.webbasetemplatemodule.activity

import android.content.Intent
import android.os.Bundle
import com.dev.hare.webbasetemplatemodule.util.view.AlertUtil
import com.dev.hare.webbasetemplatemodule.util.CommonUtil

abstract class BaseMainActivity : BaseWebActivity() {
    abstract val introClass: Class<out BaseIntroActivity>

    final override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateInit(savedInstanceState)
        if (CommonUtil.isNetworkState(applicationContext) === CommonUtil.NetworkState.NOT_CONNECTED) {
            AlertUtil.alert(this, "네트워크 신호가 원활 하지 않습니다\n확인 후 다시 접속 바랍니다")
        } else {
            // 인트로 페이지 추가
            val introIntent = Intent(this, introClass)
            introIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            startActivity(introIntent)
        }
        onCreateAfter(savedInstanceState)
    }
}