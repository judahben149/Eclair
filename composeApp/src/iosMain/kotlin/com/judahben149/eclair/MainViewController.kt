package com.judahben149.eclair

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.judahben149.eclair.di.dataStoreModule
import com.judahben149.eclair.di.platformModule
import com.judahben149.eclair.di.sharedModules
import com.judahben149.eclair.navigation.DefaultRootComponent
import org.koin.core.context.startKoin
import org.koin.core.error.KoinApplicationAlreadyStartedException

fun MainViewController() = ComposeUIViewController {

    // Initialize Koin only once
    remember {
        try {
            val iosModules = listOf(platformModule(), dataStoreModule)
            startKoin {
                modules(sharedModules + iosModules)
            }
        } catch (e: KoinApplicationAlreadyStartedException) {
            // Koin already started, ignore
        }
        true
    }

    val lifecycle = remember { LifecycleRegistry() }
    val rootComponent = remember { DefaultRootComponent(DefaultComponentContext(lifecycle)) }

    EclairApp(rootComponent)
}