package com.judahben149.eclair.domain.repository

import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {

    fun onNewChatResponse(chat: ChatMessage)

    suspend fun saveChat(chat: ChatMessage)

    fun observeAllChats(conversationId: String): Flow<List<ChatMessage>>
}