package com.judahben149.eclair

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform