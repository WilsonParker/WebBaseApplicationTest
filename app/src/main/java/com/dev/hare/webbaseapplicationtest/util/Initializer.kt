package com.dev.hare.webbaseapplicationtest.util

import android.content.Context
import com.dev.hare.hareutilitymodule.data.abstracts.Initializable
import com.dev.hare.webbasetemplatemodule.util.UrlUtil

object Initializer : Initializable {
    override fun initialize(context: Context) {
        UrlUtil.appendSubDomain("store")
        UrlUtil.appendSubDomain("mstore")
        UrlUtil.appendSubDomain("mobile-enter6")
    }
}