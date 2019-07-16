package com.dev.hare.daisowebviewtest.social

import com.dev.hare.socialloginmodule.activity.basic.BasicPaycoActivity

class BasicPaycoActivity : BasicPaycoActivity() {

    override fun redirectUrl(): String{
        return "/auth/social/payco/token-to-login?${getRedirectUrlParam()}"
    }

}
