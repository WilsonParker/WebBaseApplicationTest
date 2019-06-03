package com.dev.hare.webbaseapplicationtest.activity

import android.os.Bundle
import android.widget.ImageView
import com.dev.hare.hareutilitymodule.util.Logger
import com.dev.hare.hareutilitymodule.util.file.PreferenceUtil
import com.dev.hare.webbaseapplicationtest.R
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseIntroActivity() {
    override val imageView: ImageView
        get() = intro
    override val introImageID: Int
        get() = R.drawable.intro
    override val launchTimeOut: Long
        get() = 500
    override val splashTimeOut: Long
        get() = 2000

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_intro)
    }

    override fun init() {
        PreferenceUtil.init(resources.getString(R.string.app_name))

        /*BasicMobileCallService.getIntroImageUrl {
            Logger.log(Logger.LogType.INFO, "getIntroImageUrl : ${it.toString()}")
            imageUrl = BasicMobileCallService.baseUrl+it?.data?.get("url").toString()
            applySplash()
        }*/
    }

}