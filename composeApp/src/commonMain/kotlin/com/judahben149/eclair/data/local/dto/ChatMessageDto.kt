package com.judahben149.eclair.data.local.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.judahben149.eclair.domain.enums.MessageOrigin

expect fun generateUUID(): String
expect fun getCurrentTimeMillis(): Long

@Entity
data class ChatMessageDto(
    @PrimaryKey(autoGenerate = false)
    val id: String = generateUUID(),
    val conversationId: String,
    val message: String,
    val origin: MessageOrigin,
    val timestamp: Long = getCurrentTimeMillis()
)
