package com.judahben149.eclair

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.judahben149.eclair.presentation.screens.chat.ChatScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun EclairApp() {
    MaterialTheme {
        Scaffold {
            ChatScreen()
        }
    }
}