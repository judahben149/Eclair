package com.judahben149.eclair

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.judahben149.eclair.di.dataStoreModule
import com.judahben149.eclair.di.platformModule
import com.judahben149.eclair.di.sharedModules
import org.koin.core.context.startKoin

fun main() = application {

    val desktopModules = listOf(platformModule(), dataStoreModule)

    startKoin {
        modules(sharedModules + desktopModules)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Eclair",
    ) {
        EclairApp()
    }
}