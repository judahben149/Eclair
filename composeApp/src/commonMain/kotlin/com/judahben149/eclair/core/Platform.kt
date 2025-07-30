package com.judahben149.eclair.core

import com.judahben149.eclair.domain.enums.PlatformType

expect class Platform {
    val type: PlatformType
}