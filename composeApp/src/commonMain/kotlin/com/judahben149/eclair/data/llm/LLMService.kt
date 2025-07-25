package com.judahben149.eclair.data.llm

import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface LLMService {
    suspend fun sendMessage(message: String, conversationHistory: List<ChatMessage>): Flow<String>
    fun isAvailable(): Boolean
    val serviceType: LLMServiceType
}