package co.kr.daisomall.application

import co.kr.daisomall.constants.Constants
import co.kr.daisomall.util.Initializer
import com.dev.hare.socialloginmodule.application.GlobalApplication

class GlobalApplication : GlobalApplication() {
    override fun onCreate() {
        super.onCreate()
//        PushModuleInitializer.initialize(this)
        Initializer.initialize(this)
        Constants.initialize(this)
    }
}
