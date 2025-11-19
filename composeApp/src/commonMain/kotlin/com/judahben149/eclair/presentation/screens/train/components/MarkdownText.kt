package com.judahben149.eclair.presentation.screens.train.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MarkdownText(
    markdown: String,
    modifier: Modifier = Modifier
) {
    // Using simple custom markdown renderer
    // Supports: headers, bold, italic, code, lists, horizontal rules
    SimpleMarkdownText(
        markdown = markdown,
        modifier = modifier
    )
}
