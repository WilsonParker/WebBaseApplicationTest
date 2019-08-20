package co.kr.daisomall.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import co.kr.daisomall.R

class IntroActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        Handler().postDelayed({
            startActivity(Intent(this@IntroActivity, MainWithIntroActivity::class.java))
            overridePendingTransition(0, 0)
            finish()
        }, 1500)
    }

}