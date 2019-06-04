package com.dev.hare.webbasetemplatemodule

import com.dev.hare.webbasetemplatemodule.util.UrlUtil
import org.junit.Test
import java.util.regex.Pattern


class UrlTest {
    @Test
    fun isCurrentURL() {
        // https?://(\w*:\w*@)?[-\w.]+(:\d+)?(/([\w/_.]*(\?\S+)?)?)?
        var host = "http://mobile-enter6.dv9163.kro.kr:8001"
        // $urlReg = "^https?://(\w*:\w*@)?[-\w.]+(:\d+)?^";
//        val _hostReg = """https?://($host)?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
        val _hostReg = """https?://(mobile-enter6.dv9163.kro.kr:8001/)?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
        val _hostReg2 = """https?://""".toRegex()
        System.out.println(host.matches(_hostReg))
        System.out.println(host.replace(_hostReg2, ""))
    }

    @Test
    fun isCurrentUrl2() {
        var host = "http://mobile-enter6.dv9163.kro.kr:8001"
        var url = "http://mobile-enter6.dv9163.kro.kr:8001/auth/login"
        val _hostReg = """($host)?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
        val _hostReg2 = """(https?)?(://)?(mobile-enter6.dv9163.kro.kr)?(:([^\/]*))?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()

        System.out.println(url.matches(_hostReg))
        System.out.println(host.matches(_hostReg))
        System.out.println(url.matches(_hostReg2))
        System.out.println(host.matches(_hostReg2))

        System.out.println(UrlUtil.getRex(host))
        System.out.println(UrlUtil.getRex(url))
        System.out.println(url.matches(UrlUtil.getRex(host)))
        System.out.println(host.matches(UrlUtil.getRex(url)))
        System.out.println(UrlUtil.isCurrentHost(host, url))
        System.out.println(UrlUtil.extractCurrentDomain(host))
        System.out.println(UrlUtil.extractCurrentDomain(url))

        System.out.println(host.dropLast(1))
        System.out.println(host.takeLast(1))

        System.out.println(url.replace("^(https?)?(://)?([\\w-_]*)(.)?".toRegex(), ""))
    }

    @Test
    fun isCurrentUrl3() {
        var host = "https://www.facebook.com/enter6onlinemall/"
        var host2 = "https://m.facebook.com/enter6onlinemall/"
        var host3 = "https://mobile.facebook.com/enter6onlinemall/"
        var host4 = "www.facebook.com"
        var host5 = "m.facebook.com"
        val hostReg2 = """https?://(m|www)?(.)?""".toRegex()
        val hostReg = """https?://(m|www)?(.)?(${UrlUtil.extractLastSlashUrl(host).replace(hostReg2, "")})?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
        System.out.println(UrlUtil.extractCurrentDomain(host))
        System.out.println(UrlUtil.extractCurrentDomain(host2))
        System.out.println(UrlUtil.extractCurrentDomain(host3))
        System.out.println(UrlUtil.extractCurrentDomain(host4))
        System.out.println(UrlUtil.extractCurrentDomain(host5))
        System.out.println(host.matches(hostReg))
        System.out.println(host2.matches(hostReg))
        System.out.println(host3.matches(hostReg))
        System.out.println(host4.matches(hostReg))
        System.out.println(host5.matches(hostReg))

        var url1 = "http://mstore.enter6.co.kr/events/all-store-events/50"
        println(UrlUtil.extractCurrentDomain(url1))

        // var r1 = UrlUtil.extractCurrentDomain(url1) != "mstore.enter6.co.kr" && !UrlUtil.isCurrentHost(host, url1) || !UrlUtil.isCurrentDomain(host, url1)
        var r1 = UrlUtil.extractCurrentDomain(url1) != "mstore.enter6.co.kr" && !UrlUtil.isCurrentHost(host, url1)
        println(r1)
    }

    @Test
    fun isCurrentUrl4(){
        val testurl = "https://goodidea.tistory.com/qr/aaa/ddd.html?abc=def&ddd=fgf#sharp"
        val testurl2 = "http://mobile-enter6.dv9163.kro.kr:8001/common/identifyUser/checkplus"
        //                                     http:       //  도메인명             :포트                     경로                                파일명                    쿼리                         앵커
        val urlPattern =
            Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$")

        val mc = urlPattern.matcher(testurl2)

        if (mc.matches()) {
            for (i in 0..mc.groupCount())
                println("group(" + i + ") = " + mc.group(i))
        } else
            println("not found")

        println("domain : ${mc.group(2)}")
    }

    @Test
    fun isCurrentUrl5(){
        val urlPattern =
            Pattern.compile("^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?$")
        val url1 = "http://mobile-enter6.dv9163.kro.kr:8001/common/identifyUser/checkplus"
        val url2 = "http://mobile-enter6.dv9163.kro.kr:8001"
        val mc = urlPattern.matcher(url1)
        if (mc.matches()) {
            for (i in 0..mc.groupCount())
                println("group(" + i + ") = " + mc.group(i))
        } else
            println("not found")
        println(UrlUtil.isCurrentDomain(url1, url2))
    }

    @Test
    fun isCurrentUrl6(){
        var url = "http://mobile-enter6.dv9163.kro.kr"
        var host = "http://mobile-enter6.dv9163.kro.kr"
        // var url = "https://www.facebook.com/enter6onlinemall/"

        println(UrlUtil.extractCurrentDomain(url))
        println(UrlUtil.isCurrentHost(host, url))
        println("isNewWindow ${isNewWindow(host, url)}")

        host = "http://blog.enter6.co.kr"
        url = "http://blog.enter6.co.kr/m"
        println(UrlUtil.isCurrentHost(host, url))

        host = "https://www.facebook.com/enter6onlinemall/"
        url = "https://m.facebook.com/enter6onlinemall//m"
        println(UrlUtil.isCurrentHost(host, url))
        println(UrlUtil.isCurrentDomain(host, url))
        println(UrlUtil.extractCurrentDomain(host))
        println(UrlUtil.extractCurrentDomain(url))

        println(UrlUtil.extractCurrentHost("mstore.enter6.co.kr"))
        println("isNewWindow ${isNewWindow(host, url)}")

        host = "http://store.enter6.co.kr/events/all-store-events/50"
        url = "http://mstore.enter6.co.kr/events/all-store-events/50?_url=%2Fevents%2Fall-store-events%2F50"
        println(UrlUtil.isCurrentDomain("http://mobile-enter6.dv9163.kro.kr", host))
        println(UrlUtil.isCurrentDomain(host, url))
        println(UrlUtil.isCurrentHost(host, url))
        println(UrlUtil.isCurrentHost(url, "mstore.enter6.co.kr"))
        println(UrlUtil.extractCurrentDomain(host) != "mstore.enter6.co.kr")

        println("isNewWindow ${isNewWindow(host, url)}")
    }

    val APP_URL = "http://mobile-dev.enter6.co.kr/"
    @Test
    fun testNewWindow(){
        UrlUtil.appendSubDomain("mstore")
        UrlUtil.appendSubDomain("store")
        val local = APP_URL
        var host = local
        var url = "http://store.enter6.co.kr/events/cultures/61"
        println(UrlUtil.extractCurrentHost(local))
        println(UrlUtil.extractCurrentDomain(local))
        println(UrlUtil.extractCurrentDomain(local, true))
        println(UrlUtil.extractCurrentHost(url))
        println(UrlUtil.extractCurrentDomain(url))
        println(UrlUtil.extractCurrentDomain(url, true))
        println("isNewWindow ${isNewWindow(host, url)}")
        println("==============================")

        host = url
        url = "http://mstore.enter6.co.kr/events/cultures/61?_url=%2Fevents%2Fcultures%2F61"
        println(UrlUtil.extractCurrentHost(url))
        println(UrlUtil.extractCurrentDomain(url))
        println(UrlUtil.extractCurrentDomain(url, true))
        println(UrlUtil.extractCurrentHost(host))
        println(UrlUtil.extractCurrentDomain(host))
        println(UrlUtil.extractCurrentDomain(host, true))
        println("isNewWindow ${isNewWindow(host, url)}")
        println("==============================")

        host = local
        url = "http://mstore.enter6.co.kr/events/all-store-events/50?_url=%2Fevents%2Fall-store-events%2F50"
        println("isNewWindow ${isNewWindow(host, url)}")

        println("==============================")
        host = local
        url = "http://www.facebook.com"
        println("isNewWindow ${isNewWindow(host, url)}")
        host = url
        url = "http://m.facebook.com"
        println("isNewWindow ${isNewWindow(host, url)}")
        println("==============================")

        host = "https://twitter.com/Enter_6"
        url = "https://mobile.twitter.com/Enter_6"
        println(UrlUtil.extractCurrentHost(host))
        println(UrlUtil.extractCurrentDomain(host))
        println(UrlUtil.extractCurrentDomain(host, true))
        println(UrlUtil.extractCurrentHost(url))
        println(UrlUtil.extractCurrentDomain(url))
        println(UrlUtil.extractCurrentDomain(url, true))
        println("isNewWindow ${isNewWindow(host, url)}")
        println("==============================")
    }

    private fun isNewWindow(host:String, url:String) :Boolean{
        return if(UrlUtil.isCurrentDomain(APP_URL, host)){
            println("isCurrentDomain in")
            !UrlUtil.isCurrentHost(host, url.toString())
        } else {
            println("isCurrentDomain out")
            !UrlUtil.isCurrentDomain(host, url.toString(), false)
        }
    }
}