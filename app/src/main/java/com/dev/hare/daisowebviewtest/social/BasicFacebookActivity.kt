package com.dev.hare.daisowebviewtest.social

import com.dev.hare.socialloginmodule.activity.basic.BasicFacebookActivity


class BasicFacebookActivity : BasicFacebookActivity() {

    override fun redirectUrl(): String{
        return "/auth/social/facebook/token-to-login?${getRedirectUrlParam()}"
    }
}
