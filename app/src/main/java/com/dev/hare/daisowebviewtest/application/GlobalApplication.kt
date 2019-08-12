package com.dev.hare.daisowebviewtest.application

import com.dev.hare.daisowebviewtest.constants.Constants
import com.dev.hare.daisowebviewtest.util.Initializer
import com.dev.hare.socialloginmodule.application.GlobalApplication

class GlobalApplication : GlobalApplication() {
    override fun onCreate() {
        super.onCreate()
//        PushModuleInitializer.initialize(this)
        Initializer.initialize(this)
        Constants.initialize(this)
    }
}
