package com.judahben149.eclair.presentation.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.judahben149.eclair.data.local.dto.getCurrentTimeMillis
import com.judahben149.eclair.domain.enums.MessageOrigin
import com.judahben149.eclair.domain.model.ChatMessage
import com.judahben149.eclair.presentation.screens.chat.components.ChatHeader
import com.judahben149.eclair.presentation.screens.chat.components.ChatMessagesList
import com.judahben149.eclair.presentation.screens.chat.components.MessageInputArea
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen() {
    val viewModel = koinViewModel<ChatViewModel>()
    val state by viewModel.state.collectAsState()

    ChatScreenContent(
        messages = state.chats,
        onSendMessage = { message ->
            viewModel.saveChat(
                ChatMessage(
                    id = "",
                    conversationId = "conversationId",
                    message = message,
                    origin = MessageOrigin.SENT,
                    timestamp = getCurrentTimeMillis()
                )
            )
        }
    )
}

@Composable
fun ChatScreenContent(
    messages: List<ChatMessage>,
    onSendMessage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .windowInsetsPadding(WindowInsets.ime)
            .background(MaterialTheme.colorScheme.background)
    ) {

        ChatHeader()

        ChatMessagesList(
            messages = messages,
            modifier = Modifier.weight(1f)
        )

        MessageInputArea(
            onSendMessage = onSendMessage
        )
    }
}

@Preview
@Composable
fun ChatScreenPreview() {
    MaterialTheme {
        val sampleMessages = listOf(
            ChatMessage(
                id = "1",
                conversationId = "conv1",
                message = "Hello! How can I help you today?",
                origin = MessageOrigin.RECEIVED,
                timestamp = getCurrentTimeMillis() - 60000
            ),
            ChatMessage(
                id = "2",
                conversationId = "conv1",
                message = "I need help with my Compose Multiplatform project",
                origin = MessageOrigin.SENT,
                timestamp = getCurrentTimeMillis() - 30000
            ),
            ChatMessage(
                id = "3",
                conversationId = "conv1",
                message = "I'd be happy to help! What specific issue are you facing with your Compose Multiplatform project?",
                origin = MessageOrigin.RECEIVED,
                timestamp = getCurrentTimeMillis(),
                isStreaming = true
            )
        )

        ChatScreenContent(
            messages = sampleMessages,
            onSendMessage = { }
        )
    }
}