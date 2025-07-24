package com.judahben149.eclair.presentation.screens.chat

import androidx.lifecycle.ViewModel
import com.judahben149.eclair.domain.usecase.ObserveAllChatsUseCase
import com.judahben149.eclair.domain.usecase.SaveChatUseCase

class ChatViewModel(
    private val saveChatUseCase: SaveChatUseCase,
    private val observeAllChatsUseCase: ObserveAllChatsUseCase
): ViewModel() {


}