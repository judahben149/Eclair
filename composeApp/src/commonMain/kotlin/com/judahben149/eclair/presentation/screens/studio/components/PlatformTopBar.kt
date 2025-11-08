package com.judahben149.eclair.presentation.screens.studio.components

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformTopBar(
    title: String,
    onBackClick: () -> Unit
)
