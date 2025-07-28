package com.judahben149.eclair.data.llm.impl

import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DummyLLMService : LLMService {
    private val responses = listOf(
        "That's an interesting point!",
        "I understand what you mean.",
        "Can you tell me more about that?",
        "That makes sense to me."
    )
    
    override suspend fun sendMessage(
        message: String, 
        conversationHistory: List<ChatMessage>
    ): Flow<String> = flow {
        val response = responses.random()
//        emit(response)
        response.forEach { char ->
            emit(char.toString())
            delay(3) // Simulate typing
        }
    }
    
    override fun isAvailable(): Boolean = true
    override val serviceType = LLMServiceType.DUMMY
}