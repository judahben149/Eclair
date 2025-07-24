package com.judahben149.eclair.domain.usecase

import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveAllChatsUseCase(private val repository: ChatRepository) {

    operator fun invoke(conversationId: String): Flow<List<ChatMessage>> {
        return repository.observeAllChats(conversationId)
    }
}