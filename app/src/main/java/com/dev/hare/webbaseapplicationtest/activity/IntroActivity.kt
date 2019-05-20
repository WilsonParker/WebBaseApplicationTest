package com.dev.hare.webbaseapplicationtest.activity

import android.app.Activity
import android.os.Bundle
import android.widget.ImageView
import com.dev.hare.firebasepushmodule.basic.BasicMobileCallService
import com.dev.hare.firebasepushmodule.util.Logger
import com.dev.hare.webbaseapplicationtest.R
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseIntroActivity() {
    override lateinit var imageUrl: String
    override val introImageID: Int
        get() = R.drawable.intro
    override val startActivityClass: Class<Activity>
        get() = MainActivity::class.java as Class<Activity>
    override val launchTimeOut: Long
        get() = 500
    override val splashTimeOut: Long
        get() = 2000

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_intro)
    }

    override fun init() {
        BasicMobileCallService.getIntroImageUrl {
            Logger.log(Logger.LogType.INFO, "getIntroImageUrl : ${it.toString()}")
            // imageUrl = "https://pbs.twimg.com/media/DxxPcBZVAAA9MHr.jpg"
            imageUrl = BasicMobileCallService.baseUrl+it?.data?.get("url").toString()
            applySplash()
        }
    }

    override fun onCreateAfter(savedInstanceState: Bundle?) {
    }

    override fun getIntroImageView(): ImageView {
        return intro
    }
}