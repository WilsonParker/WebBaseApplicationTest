package com.dev.hare.daisowebviewtest.util

import android.content.Context
import com.dev.hare.apputilitymodule.data.abstracts.Initializable
import com.dev.hare.apputilitymodule.util.file.PreferenceUtil
import com.dev.hare.daisowebviewtest.R

object Initializer : Initializable {
    override fun initialize(context: Context) {
//        UrlUtil.appendSubDomain("store")
//        UrlUtil.appendSubDomain("mstore")
//        UrlUtil.appendSubDomain("mobile-enter6")

        PreferenceUtil.setPreferenceName(context.resources.getString(R.string.app_name))
        PreferenceUtil.initialize(context)
    }
}