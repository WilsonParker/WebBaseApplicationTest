package com.dev.hare.daisowebviewtest.constants

import android.content.Context
import com.dev.hare.apputilitymodule.data.abstracts.Initializable

object Constants : Initializable {
    enum class BuildType {
        LOCAL, DEBUG, RELEASE
    }

    private val build = BuildType.DEBUG

    var APP_URL = ""
    var APP_API_URL = ""
    var IMG_URL = "https://m.livedev.daisomall.co.kr/"

    var URL_KEY = "daiso"
    var API_KEY = "475cc8db07ce29355be7c0411a142b5edbcb0d2539110d97390f98002af4543f"
    var SHOW_LOG = true

    override fun initialize(context: Context) {
        when (build) {
            BuildType.LOCAL -> {
                // APP_URL = "http://m.quickdev.daisomall.co.kr/"
                APP_URL = "https://m.livedev.daisomall.co.kr/"
                APP_API_URL = "https://m.livedev.daisomall.co.kr/"
                IMG_URL = "https://m.livedev.daisomall.co.kr/"
            }
            BuildType.DEBUG -> {
                APP_URL = "https://m.livedev.daisomall.co.kr/"
                APP_API_URL = "https://m.livedev.daisomall.co.kr/"
                IMG_URL = "https://m.livedev.daisomall.co.kr/"
            }
            BuildType.RELEASE -> {
                // APP_URL = "https://m.daisomall.co.kr/"
                APP_URL = "http://app.daisomall.co.kr/"
                APP_API_URL = "http://app.daisomall.co.kr/"
                IMG_URL = "http://app.daisomall.co.kr/"
            }

        }
    }

}
