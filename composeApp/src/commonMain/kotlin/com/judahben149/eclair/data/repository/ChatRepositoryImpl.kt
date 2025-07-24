package com.judahben149.eclair.data.repository

import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.repository.ChatRepository

class ChatRepositoryImpl(eclairDatabase: EclairDatabase): ChatRepository {
    override fun onNewChatResponse(chat: ChatMessage) {
        TODO("Not yet implemented")
    }

    override fun saveChat(chat: ChatMessage) {

    }


}