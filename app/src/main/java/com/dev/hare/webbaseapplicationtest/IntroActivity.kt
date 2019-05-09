package com.dev.hare.webbaseapplicationtest

import android.os.Bundle
import android.widget.ImageView
import com.dev.hare.webbasetemplatemodule.activity.BaseIntroActivity
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : BaseIntroActivity() {
    override val imageUrl: String
         get() = "https://pbs.twimg.com/media/DxxPcBZVAAA9MHr.jpg"
//        get() = "https://www.pixelstalk.net/wp-content/uploads/2016/08/3D-Phone-Image-Tumblr-349x620.jpg"
    override val introImageID: Int
        get() = R.drawable.intro
    override val launchTimeOut: Long
        get() = 2000
    override val splashTimeOut: Long
        get() = 10000

    override fun onCreateInit(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_intro)
    }

    override fun getIntroImageView(): ImageView {
        return intro
    }
}