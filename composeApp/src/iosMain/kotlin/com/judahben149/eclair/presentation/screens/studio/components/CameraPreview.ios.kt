package com.judahben149.eclair.presentation.screens.studio.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
actual fun CameraPreview(
    modifier: Modifier,
    onImageCaptured: (Any) -> Unit,
    onAnalyzeFrame: (Any) -> Unit
) {
    // Placeholder for iOS camera implementation
    // Real implementation would use AVFoundation through Kotlin/Native interop
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "iOS Camera Preview\n(AVFoundation integration pending)",
            color = Color.White
        )
    }
}