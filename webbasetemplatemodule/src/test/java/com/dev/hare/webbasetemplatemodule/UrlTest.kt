package com.dev.hare.webbasetemplatemodule

import org.junit.Test

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

        var url = "http://mobile-enter6.dv9163.kro.kr:8001/auth/login"
        val _hostReg3 = """($host)?(/([\w/_.]*(\?\S+)?)?)?""".toRegex()
        System.out.println(url.matches(_hostReg3))

        System.out.println(host.dropLast(1))
        System.out.println(host.takeLast(1))

    }
}