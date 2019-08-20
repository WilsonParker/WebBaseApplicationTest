package co.kr.daisomall.social

import com.dev.hare.socialloginmodule.activity.basic.BasicKakaoActivity

class BasicKakaoActivity : BasicKakaoActivity() {

    override fun redirectUrl(): String{
        return "/auth/social/kakao/token-to-login?${getRedirectUrlParam()}"
    }
}
