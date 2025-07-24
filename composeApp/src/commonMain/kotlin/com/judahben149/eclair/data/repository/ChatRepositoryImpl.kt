package com.judahben149.eclair.data.repository

import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.mapper.toChatMessage
import com.judahben149.eclair.data.mapper.toChatMessageEntity
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepositoryImpl(private val database: EclairDatabase): ChatRepository {
    override fun onNewChatResponse(chat: ChatMessage) {
        TODO("Not yet implemented")
    }

    override suspend fun saveChat(chat: ChatMessage) {
        database.chatDao().insertChat(chat.toChatMessageEntity())
    }

    override fun observeAllChats(conversationId: String): Flow<List<ChatMessage>> {
        return database.chatDao().observeAllChats(conversationId)
            .map { messages -> messages.map { it.toChatMessage() } }
    }
}