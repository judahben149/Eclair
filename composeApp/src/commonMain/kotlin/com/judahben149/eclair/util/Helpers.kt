package com.judahben149.eclair.util

expect fun generateUUID(): String
expect fun getCurrentTimeMillis(): Long


expect fun logThis(tag: String = "Eclair", message: String)

fun Any.logIt(tag: String = "Eclair", message: String? = null) {
    val logMessage = message ?: this.toString()
    logThis(tag, logMessage)
}

fun String.logIt(tag: String = "Eclair") {
    logThis(tag, this)
}