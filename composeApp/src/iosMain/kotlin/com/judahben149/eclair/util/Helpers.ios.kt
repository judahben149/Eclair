package com.judahben149.eclair.util

import platform.Foundation.NSDate
import platform.Foundation.NSLog
import platform.Foundation.NSUUID
import platform.Foundation.timeIntervalSince1970

actual fun generateUUID(): String = NSUUID().UUIDString()

actual fun getCurrentTimeMillis(): Long = (NSDate().timeIntervalSince1970 * 1000).toLong()

actual fun logThis(tag: String, message: String) {
    NSLog("[$tag] $message")
}