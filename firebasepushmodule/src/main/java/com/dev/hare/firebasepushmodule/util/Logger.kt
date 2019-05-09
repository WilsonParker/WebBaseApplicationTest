package com.dev.hare.firebasepushmodule.util

import android.util.Log

object Logger {
    var IS_DEBUG = true

    enum class LogType {
        INFO, DEBUG, VERB, WARN, ERROR
    }

    fun log(logType: LogType, e: Exception) {
        log(logType, "", e)
    }

    fun log(logType: LogType, message: String, e: Exception) {
        log(logType, message + e.message)
        e.printStackTrace()
    }

    fun log(logType: LogType, t: Throwable) {
        log(logType, "", t)
    }

    fun log(logType: LogType, message: String, t: Throwable) {
        log(logType, message + t.message)
        t.printStackTrace()
    }

    fun log(logType: LogType, message: String) {
        if (IS_DEBUG) {
            val log = buildLogMsg(message)
            when (logType) {
                Logger.LogType.INFO -> Log.i(log[0], log[1])
                Logger.LogType.DEBUG -> Log.d(log[0], log[1])
                Logger.LogType.VERB -> Log.v(log[0], log[1])
                Logger.LogType.WARN -> Log.w(log[0], log[1])
                Logger.LogType.ERROR -> Log.e(log[0], log[1])
            }
        }
    }

    private fun buildLogMsg(message: String): Array<String> {
        val ste = Thread.currentThread().stackTrace[4]
        return arrayOf(ste.fileName.replace(".java", ""), "[ ${ste.methodName} ] : $message")
    }
}