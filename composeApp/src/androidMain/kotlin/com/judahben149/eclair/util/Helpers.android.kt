package com.judahben149.eclair.util

import android.util.Log
import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()

actual fun logThis(tag: String, message: String) {
    Log.d(tag, message)
}