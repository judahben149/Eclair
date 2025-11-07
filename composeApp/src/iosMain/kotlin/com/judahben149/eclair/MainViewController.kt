package com.judahben149.eclair

import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.judahben149.eclair.di.dataStoreModule
import com.judahben149.eclair.di.platformModule
import com.judahben149.eclair.di.sharedModules
import com.judahben149.eclair.navigation.DefaultRootComponent
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {

    val iosModules = listOf(platformModule(), dataStoreModule)

    startKoin {
        modules(sharedModules + iosModules)
    }

    val lifecycle = LifecycleRegistry()
    val rootComponent = DefaultRootComponent(DefaultComponentContext(lifecycle))

    EclairApp(rootComponent)
}