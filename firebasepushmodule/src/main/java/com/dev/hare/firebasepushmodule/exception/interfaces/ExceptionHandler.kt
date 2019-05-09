package com.dev.hare.firebasepushmodule.exception.interfaces

interface ExceptionHandler {
    fun <T> handleException(def: T?): T?
}
