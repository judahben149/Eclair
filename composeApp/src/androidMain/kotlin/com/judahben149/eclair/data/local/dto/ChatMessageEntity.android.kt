package com.judahben149.eclair.data.local.dto

import java.util.UUID

actual fun generateUUID(): String = UUID.randomUUID().toString()

actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()