package com.dev.hare.webbasetemplatemodule.util

import java.util.regex.Pattern

object UrlUtil {
    const val PreHost = "^(https?)?(://)?(mobile.|m.|www.)?"
    private val _hostReg = """$PreHost""".toRegex()
    private val urlPattern =
        Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$")

    fun getRex(host: String): Regex {
        return """$PreHost(${extractCurrentUrl(host)})?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
    }

    fun isCurrentHost(host: String, url: String): Boolean {
        return url.matches(getRex(host))
    }

    fun isCurrentDomain(t1: String, t2: String): Boolean {
        val m1 = urlPattern.matcher(appendLastSlashUrl(t1))
        val m2 = urlPattern.matcher(appendLastSlashUrl(t2))
        var domain1 = if (m1.matches()) extractCurrentUrl(m1.group(2)) else ""
        var domain2 = if (m2.matches()) extractCurrentUrl(m2.group(2)) else ""
        return domain1 == domain2
    }

    fun extractLastSlashUrl(url: String): String {
        return if (url.takeLast(1) == "/") url.dropLast(1) else url
    }

    fun appendLastSlashUrl(url: String): String {
        return if (url.takeLast(1) != "/") "$url/" else url
    }

    fun extractCurrentUrl(url: String): String {
        return extractLastSlashUrl(url).replace(_hostReg, "")
    }


}