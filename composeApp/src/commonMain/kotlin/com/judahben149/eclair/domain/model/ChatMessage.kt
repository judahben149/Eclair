package com.judahben149.eclair.domain.model

import com.judahben149.eclair.domain.enums.MessageOrigin

data class ChatMessage(
    val id: String,
    val conversationId: String,
    val message: String,
    val origin: MessageOrigin,
    val timestamp: Long,
    val isStreaming: Boolean = false
)