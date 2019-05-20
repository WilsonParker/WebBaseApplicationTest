package com.dev.hare.socialloginmodule.activity.abstracts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class AbstractSocialActivity : AppCompatActivity() {
    companion object {
        const val ACTION_TYPE = "type"
        const val RESULT_CODE = 0x0010
        const val RESULT_CODE_SUCCESS = 0x0001
        const val RESULT_CODE_FAIL = 0x0010
    }

    enum class ActionType(private val value: String) {
        LogIn("logIn"),
        LogOut("logOut"),
        UnLink("unLink");

        fun getValue() = value
    }

    enum class Key(private val value: String) {
        AccessToken("accessToken"),
        RefreshToken("refreshToken"),
        Id("Id");

        fun getValue() = value
    }

    protected var accessToken: String? = null
    protected var refreshToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        when (intent.getSerializableExtra(ACTION_TYPE)) {
            ActionType.LogIn -> logIn()
            ActionType.LogOut -> logOut()
            ActionType.UnLink -> unLink()
        }
    }

    protected fun returnMainActivity(accessToken: String?, refreshToken: String?, id: String?) {
        val kIntent = Intent()
        kIntent.putExtra(Key.AccessToken.getValue(), accessToken)
        kIntent.putExtra(Key.RefreshToken.getValue(), refreshToken)
        kIntent.putExtra(Key.Id.getValue(), id)
        setResult(RESULT_CODE_SUCCESS, kIntent)
        finish()
    }

    abstract fun logIn()
    abstract fun logOut()
    abstract fun unLink()
}