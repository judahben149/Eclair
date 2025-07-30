package com.judahben149.eclair.core

import android.app.Application
import com.judahben149.eclair.domain.enums.PlatformType

actual class Platform(
    val application: Application,
) {
    actual val type = PlatformType.Android
}