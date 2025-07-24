package com.judahben149.eclair.data.mapper

import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.domain.model.ChatMessage

fun ChatMessage.toChatMessageEntity(): ChatMessageDto {
    return ChatMessageDto(
        conversationId = conversationId,
        message = message,
        origin = origin,
        timestamp = timestamp
    )
}

fun ChatMessageDto.toChatMessage(): ChatMessage {
    return ChatMessage(
        id = id,
        conversationId = conversationId,
        message = message,
        origin = origin,
        timestamp = timestamp
    )
}