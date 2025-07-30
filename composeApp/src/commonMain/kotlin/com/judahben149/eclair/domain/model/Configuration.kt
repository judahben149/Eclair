package com.judahben149.eclair.domain.model

import com.judahben149.eclair.core.Platform
import com.judahben149.eclair.domain.enums.BuildType

class Configuration(
    val platform: Platform,
    val buildType: BuildType
)