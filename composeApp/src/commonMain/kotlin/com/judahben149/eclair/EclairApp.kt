package com.judahben149.eclair

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.judahben149.eclair.navigation.DefaultRootComponent
import com.judahben149.eclair.navigation.RootComponent
import com.judahben149.eclair.presentation.navigation.RootContent
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun EclairApp(rootComponent: RootComponent) {
    MaterialTheme {
        RootContent(rootComponent)
    }
}