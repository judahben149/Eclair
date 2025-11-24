package com.judahben149.eclair.presentation.screens.studio.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Cross-platform camera preview composable
 * @param modifier Modifier for the camera preview
 * @param onImageCaptured Callback when user captures an image
 * @param onAnalyzeFrame Callback for real-time frame analysis (500ms interval)
 */
@Composable
expect fun CameraPreview(
    modifier: Modifier = Modifier,
    onImageCaptured: (Any) -> Unit,
    onAnalyzeFrame: (Any) -> Unit
)
