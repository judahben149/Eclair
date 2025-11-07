package com.judahben149.eclair

import androidx.compose.runtime.Composable
import com.judahben149.eclair.navigation.RootComponent
import com.judahben149.eclair.presentation.navigation.RootContent
import com.judahben149.eclair.presentation.theme.EclairTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun EclairApp(rootComponent: RootComponent) {
    EclairTheme {
        RootContent(rootComponent)
    }
}