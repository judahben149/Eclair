package com.judahben149.eclair.data.llm.impl

import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

class OnDeviceLLMService(

): LLMService {
    override suspend fun sendMessage(
        message: String,
        conversationHistory: List<ChatMessage>
    ): Flow<String> {
        TODO("Not yet implemented")
    }

    override fun isAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override val serviceType: LLMServiceType
        get() = TODO("Not yet implemented")
}