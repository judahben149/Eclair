package com.judahben149.eclair

import androidx.compose.ui.window.ComposeUIViewController
import com.judahben149.eclair.di.platformModule
import com.judahben149.eclair.di.sharedModules
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {

    val iosModules = listOf(platformModule())

    startKoin {
        modules(sharedModules + iosModules)
    }
    EclairApp()
}