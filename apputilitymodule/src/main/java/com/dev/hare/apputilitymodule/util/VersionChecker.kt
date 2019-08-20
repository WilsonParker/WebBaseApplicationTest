package com.dev.hare.apputilitymodule.util

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import org.jsoup.Jsoup
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

object VersionChecker {


    fun getVersionInfo(context: Context?): String {
        var version = "Unknown"
        var packageInfo: PackageInfo?

        if (context == null) {
            return version
        }
        try {
            packageInfo = context?.applicationContext
                .packageManager
                .getPackageInfo(context?.applicationContext.packageName, 0)
            version = packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.log(Logger.LogType.INFO, "getVersionInfo :" + e.message)
        }
        return version
    }

    /**
     * Gets market version.
     *
     * @param packageName the package name
     * @return the market version
     */
    fun getMarketVersion(packageName: String): String? {
        var versionMarket: String? = null
        try {
            var doc =
                Jsoup.connect("https://play.google.com/store/apps/details?id=" + packageName).get()
            var version = doc.select(".htlgb")
            for (i in 0..version.size) {
                versionMarket = version.get(i).text()
                if (Pattern.matches("^[0-9]{1}.[0-9]{1}.[0-9]{1}$", versionMarket)) {
                    break
                }
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return versionMarket
    }

    fun getMarketVersion(context: Context): String? {
        return getMarketVersion(context.packageName)
    }

    /**
     * Gets market version fast result.
     *
     * @param packageName the package name
     * @return the market version fast result
     */
    fun getMarketVersionFast(packageName: String): String? {
        var mData = ""
        var mVer: String?
        try {
            var mUrl = URL("https://play.google.com/store/apps/details?id=" + packageName)
            var mConnection: HttpURLConnection? = mUrl.openConnection() as HttpURLConnection
            mConnection?.let {
                it.connectTimeout = 5000
                it.useCaches = false
                it.doOutput = true

                if (it.responseCode == HttpURLConnection.HTTP_OK) {
                    var mReader = BufferedReader(InputStreamReader(mConnection.getInputStream()))
                    while (true) {
                        var line = mReader.readLine()
                        if (line == null) break
                        mData += line
                    }
                    mReader.close()
                }
                mConnection.disconnect()
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        var startToken = "<div class=\"IQ1z0d\"><span class=\"htlgb\">"
        var endToken = "</span></div>"
        var index = mData.indexOf(startToken)
        if (index == -1) {
            mVer = null
        } else {
            mVer = mData.substring(index + startToken.length, index + startToken.length + 100)
            mVer = mVer.substring(0, mVer.indexOf(endToken)).trim()
        }
        return mVer
    }

    fun getMarketVersionFast(context: Context): String? {
        return getMarketVersionFast(context.packageName)
    }

}