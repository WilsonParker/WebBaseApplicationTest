package com.dev.hare.webbaseapplicationtest.application

import com.dev.hare.socialloginmodule.application.GlobalApplication
import com.dev.hare.webbaseapplicationtest.util.Initializer

class GlobalApplication : GlobalApplication() {
    override fun onCreate() {
        super.onCreate()
//        PushModuleInitializer.initialize(this)
        Initializer.initialize(this)
    }
}
