package com.dev.hare.hareutilitymodule.util.file

import android.content.Context
import android.content.Context.MODE_PRIVATE

object PreferenceUtil {
    internal object Key {
        val VERSION = "ver"
        val OLD_VERSION = "old_ver"
    }

    private var SHARED_PREFERENCES_NAME = "PreferenceUtil"

    fun init(name: String) {
        SHARED_PREFERENCES_NAME = name
    }

    fun setData(context: Context, key: String, value: String): Boolean {
        val editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getData(context: Context, key: String): String? {
        val savedSession = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE)
        return savedSession.getString(key, "") // "" : undifined, Y : yes, N :
    }

    fun isFirstInstall(context: Context): Boolean {
        val pref = context.getSharedPreferences(Key.VERSION, MODE_PRIVATE)
        var version = 0
        var oldVer = 0
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            version = packageInfo.versionCode
            oldVer = pref.getInt(Key.OLD_VERSION, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return oldVer < version
    }

    fun completeFirstInstall(context: Context) {
        val pref = context.getSharedPreferences(Key.VERSION, MODE_PRIVATE)
        var version: Int
        var oldVer: Int
        try {
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            version = packageInfo.versionCode
            oldVer = pref.getInt(Key.OLD_VERSION, 0)
            if (oldVer < version) {
                val edit = pref.edit()
                edit.putInt(Key.OLD_VERSION, version)
                edit.commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}