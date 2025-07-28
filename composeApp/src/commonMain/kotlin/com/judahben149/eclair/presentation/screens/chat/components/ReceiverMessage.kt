package com.judahben149.eclair.presentation.screens.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.presentation.common.MessageBubble

@Composable
fun ReceiverMessage(
    message: ChatMessage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        MessageBubble(
            message = message.message + if (message.isStreaming) "..." else "",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            isStreaming = message.isStreaming,
            modifier = Modifier.widthIn(max = 280.dp)
        )
    }
}