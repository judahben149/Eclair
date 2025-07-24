package com.judahben149.eclair.domain.repository

import com.judahben149.eclair.domain.model.ChatMessage

interface ChatRepository {

    fun onNewChatResponse(chat: ChatMessage)

    fun saveChat(chat: ChatMessage)
}