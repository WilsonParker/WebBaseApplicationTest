package com.dev.hare.webbasetemplatemodule.util

import java.util.regex.Pattern

object UrlUtil {
    /**
     * 수동으로 삭제할 sub domain 들 입니다
     *
     * @author     Hare
     * @added      2019-06-03
     * @updated    2019-06-03
     * */
    var subDomain = "mobile|m|www"

    fun appendSubDomain(domain: String) {
        subDomain += "|$domain"
    }

    /**
     * sub domain 을 모두 지우는 정규식 입니다
     *
     * @author     Hare
     * @added      2019-06-03
     * @updated    2019-06-03
     * */
    val preAllHost = "(https?)?(://)?([\\w-_]*)(.)?"
    var preHost = "^(https?)?(://)?($subDomain)?"
        get() {
            return "^(https?)?(://)?(($subDomain)?(.))?"
        }

    /**
     * Url 을 분리시키는 pattern 입니다
     *
     * @author     Hare
     * @added      2019-06-03
     * @updated    2019-06-03
     * */
    private val urlPattern =
        Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$")

    fun getRex(host: String): Regex {
        return """(https?)?(://)?(${extractCurrentHost(host)})+(:([^\/]*))?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
    }

    fun isCurrentHost(host: String, url: String): Boolean {
        return url.matches(getRex(host))
    }

    fun isCurrentDomain(t1: String, t2: String): Boolean {
        return extractCurrentUrl(t1) == extractCurrentUrl(t2)
    }

    fun extractLastSlashUrl(url: String): String {
        return if (url.takeLast(1) == "/") url.dropLast(1) else url
    }

    fun appendLastSlashUrl(url: String): String {
        return if (url.takeLast(1) != "/") "$url/" else url
    }

    fun extractCurrentHost(url: String): String {
        val matcher = urlPattern.matcher(appendLastSlashUrl(url))
        return if (matcher.matches())
            extractLastSlashUrl(matcher.group(2))
        else extractLastSlashUrl(url).replace("(https?)?(://)?", "")
    }

    fun extractCurrentUrl(url: String, deep: Boolean = false): String {
        val matcher = urlPattern.matcher(appendLastSlashUrl(url))
        val reg = (if (deep) "$preAllHost" else "$preHost").toRegex()
        return if (matcher.matches())
            extractLastSlashUrl(matcher.group(2)).replace(reg, "")
        else extractLastSlashUrl(url).replace(reg, "")
    }


}