package com.judahben149.eclair.domain.usecase

import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository

class SaveChatUseCase(private val chatRepository: ChatRepository) {

    operator fun invoke(chat: ChatMessage) {
        chatRepository.saveChat(chat)
    }
}