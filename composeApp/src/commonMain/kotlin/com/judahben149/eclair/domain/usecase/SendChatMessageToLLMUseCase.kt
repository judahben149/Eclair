package com.judahben149.eclair.domain.usecase

import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class SendChatMessageToLLMUseCase(private val chatRepository: ChatRepository) {
    suspend operator fun invoke(message: String, conversationId: String): Flow<ChatMessage> {
        return chatRepository.sendMessageAndSave(message, conversationId)
    }
}