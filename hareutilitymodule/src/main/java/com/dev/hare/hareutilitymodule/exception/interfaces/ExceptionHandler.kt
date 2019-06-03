package com.dev.hare.hareutilitymodule.exception.interfaces

interface ExceptionHandler {
    fun <T> handleException(def: T?): T?
}
