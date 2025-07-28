package com.judahben149.eclair.domain.repository

import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun onNewChatResponse(chat: ChatMessage)

    suspend fun saveChat(chat: ChatMessageDto)

    fun observeAllChats(conversationId: String): Flow<List<ChatMessage>>

    suspend fun sendMessageAndSave(
        message: String,
        conversationId: String,
        serviceType: LLMServiceType = LLMServiceType.DUMMY
    ): Flow<ChatMessage>

}