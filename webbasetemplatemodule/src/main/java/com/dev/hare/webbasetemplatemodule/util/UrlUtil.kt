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

    /**
     * subDomain 을 추가합니다
     *
     * @param      domain
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
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
    val preAllHost = "^(https?)?(://)?([\\w-_]*.)?"
    /**
     * 설정한 sub domain 을 지우는 정규식 입니다
     *
     * @param
     * @return
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    var preHost =""
        get() {
            return "^(https?)?(://)?(($subDomain)+\\.)?"
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

    /**
     * host 와 url 을 비교해서 같은 host 상에 있는지 확인합니다
     * ex) m.facebook.com == facebook.com/auth/login
     *
     * @param      host
     * 현재 load 된 페이지의 url
     * @param      url
     * 이동할 url
     * @return     Boolean
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun isCurrentHost(host: String, url: String): Boolean {
        return url.matches(getRex(host))
    }

    /**
     * host 와 url 을 비교해서 같은 host 상에 있는지 확인합니다
     * ex)
     * m.facebook.com => focebook.com
     * store.facebook.com/auth/login => facebook.com
     *
     * @param      t1
     * @param      t2
     * @param      deep
     * false : 설정한 sub domain 만 제거합니다
     * true  : 모든 sub domain 을 제거합니다
     * @return     Boolean
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun isCurrentDomain(t1: String, t2: String, deep: Boolean = false): Boolean {
        return extractCurrentDomain(t1, deep) == extractCurrentDomain(t2, deep)
    }

    /**
     * url 의 마지막 / 를 제거합니다
     *
     * @param      url
     * @return     String
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun extractLastSlashUrl(url: String): String {
        return if (url.takeLast(1) == "/") url.dropLast(1) else url
    }

    /**
     * url 마지막에 / 를 추가합니다
     *
     * @param      url
     * @return     String
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun appendLastSlashUrl(url: String): String {
        return if (url.takeLast(1) != "/") "$url/" else url
    }

    /**
     * host 를 추출합니다
     * ex) http://www.facebook.com/auth/ogin -> facebook.com/auth/login
     *
     * @param      url
     * @return     String
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun extractCurrentHost(url: String): String {
        return extractLastSlashUrl(url).replace("""(https?)?(://)?""".toRegex(), "")
    }

    /**
     * domain 를 추출합니다
     * ex) http://www.facebook.com/auth/ogin -> facebook.com
     *
     * @param      url
     * @param      deep
     * false : 설정한 sub domain 만 제거합니다
     * true  : 모든 sub domain 을 제거합니다
     * @return     String
     * @author     Hare
     * @added      2019-06-04
     * @updated    2019-06-04
     * */
    fun extractCurrentDomain(url: String, deep: Boolean = false): String {
        val matcher = urlPattern.matcher(appendLastSlashUrl(url))
        val reg = (if (deep) "$preAllHost" else "$preHost").toRegex()
        return if (matcher.matches())
            extractLastSlashUrl(matcher.group(2)).replace(reg, "")
        else extractLastSlashUrl(url).replace(reg, "")
    }


}