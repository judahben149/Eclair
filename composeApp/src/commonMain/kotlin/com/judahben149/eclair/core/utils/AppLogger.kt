package com.judahben149.eclair.core.utils

expect object AppLogger {
    fun e(tag: String, message: String, throwable: Throwable? = null)
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
}

fun String.logE(throwable: Throwable? = null) = AppLogger.e("", this, throwable)
fun String.logD() = AppLogger.d("", this)
fun String.logI() = AppLogger.i("", this)