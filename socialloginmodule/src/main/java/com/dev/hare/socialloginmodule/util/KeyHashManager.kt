package com.dev.hare.socialloginmodule.util

import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.kakao.util.helper.Utility.getPackageInfo
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object KeyHashManager {

    fun getKeyHash(context: Context): String? {
        val packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES) ?: return null

        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP)
            } catch (e: NoSuchAlgorithmException) {
                Log.w(TAG, "Unable to get MessageDigest. signature=$signature", e)
            }

        }
        return null
    }

    fun getKeyHash2(context: Context): String? {
        val TAG = "KeyHash"
        var keyHash: String? = null
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                keyHash = String(Base64.encode(md.digest(), 0))
                Log.d(TAG, keyHash)
            }
        } catch (e: Exception) {
            Log.e(TAG, "name not found$e")
        }

        return keyHash
    }
}