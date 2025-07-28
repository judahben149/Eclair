package com.judahben149.eclair.data.repository

import com.judahben149.eclair.core.utils.logI
import com.judahben149.eclair.data.llm.LLMServiceManager
import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.dto.ChatMessageDto
import com.judahben149.eclair.data.local.dto.generateUUID
import com.judahben149.eclair.data.local.dto.getCurrentTimeMillis
import com.judahben149.eclair.data.mapper.toChatMessage
import com.judahben149.eclair.data.mapper.toChatMessageEntity
import com.judahben149.eclair.domain.enums.MessageOrigin
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ChatRepositoryImpl(
    private val database: EclairDatabase,
    private val llmServiceManager: LLMServiceManager
): ChatRepository {
    override fun onNewChatResponse(chat: ChatMessage) {
        TODO("Not yet implemented")
    }

    override suspend fun saveChat(chat: ChatMessageDto) {
        database.chatDao().insertChat(chat)
    }

    override fun observeAllChats(conversationId: String): Flow<List<ChatMessage>> {
        return database.chatDao().observeAllChats(conversationId)
            .map { messages -> messages.map { it.toChatMessage() } }
    }

    override suspend fun sendMessageAndSave(
        message: String,
        conversationId: String,
        serviceType: LLMServiceType
    ): Flow<ChatMessage> = flow {
        val dao = database.chatDao()

        val userChat = ChatMessage(
            id = "",
            conversationId = conversationId,
            message = message,
            origin = MessageOrigin.SENT,
            timestamp = getCurrentTimeMillis()

        )

        saveChat(userChat.toChatMessageEntity())
        emit(userChat)

        val history  = dao.getConversationHistory(conversationId).map { it.toChatMessage() }

        llmServiceManager.setPreferredService(LLMServiceType.ON_DEVICE).also {
            "Setting preferred service to ${LLMServiceType.ON_DEVICE}".logI()
        }

        val llmService = llmServiceManager.getService(serviceType).also {
            "Preferred service is ${it.serviceType}".logI()
        }

        var responseChat = ChatMessageDto(
            id = generateUUID(),
            conversationId = conversationId,
            message = "",
            origin = MessageOrigin.RECEIVED,
            timestamp = getCurrentTimeMillis(),
            isStreaming = true
        )

        llmService.sendMessage(message, history).collect { chunk ->
            responseChat = responseChat.copy(
                message = responseChat.message + chunk,
                isStreaming = chunk.isNotEmpty()
            )


//            val updatedChat = responseChat.copy(
//                message = response
//            )

            responseChat.message.logI()
            saveChat(responseChat)
            emit(responseChat.toChatMessage())
        }
    }
}