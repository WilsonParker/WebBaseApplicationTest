package co.kr.daisomall.util

import com.dev.hare.apputilitymodule.util.file.PreferenceUtil

object PrefUtil {
    private const val AUTO_LOGIN_TOKEN = "auto_login_token"

    fun getAutoLoginToken(): String {
        return PreferenceUtil.getData(AUTO_LOGIN_TOKEN)
    }

    fun setAutoLoginToken(token: String) {
        PreferenceUtil.setData(AUTO_LOGIN_TOKEN, token)
    }
}