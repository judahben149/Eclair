package com.judahben149.eclair

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Eclair",
    ) {
        App()
    }
}