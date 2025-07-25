package com.judahben149.eclair.presentation.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.domain.usecase.ObserveAllChatsUseCase
import com.judahben149.eclair.domain.usecase.SaveChatUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val saveChatUseCase: SaveChatUseCase,
    private val observeAllChatsUseCase: ObserveAllChatsUseCase
): ViewModel() {

    private val _state = MutableStateFlow(ChatState())
    val state = _state.asStateFlow()

    init {
        observeAllChats("conversationId")
    }

    fun saveChat(chat: ChatMessage) {
        viewModelScope.launch {
            saveChatUseCase(chat)
        }
    }

    fun observeAllChats(conversationId: String) {
        viewModelScope.launch {
            observeAllChatsUseCase(conversationId).collect {
                _state.value = _state.value.copy(chats = it)
            }
        }
    }
}

data class ChatState(
    val chats: List<ChatMessage> = emptyList()
)