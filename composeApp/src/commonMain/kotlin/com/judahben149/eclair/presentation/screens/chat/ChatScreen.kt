package com.judahben149.eclair.presentation.screens.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import com.judahben149.eclair.domain.enums.MessageOrigin
import com.judahben149.eclair.domain.model.ChatMessage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatScreen() {
    val viewModel = koinViewModel<ChatViewModel>()
    val state by viewModel.state.collectAsState()

    ChatContent(state) {
        viewModel.saveChat(
            ChatMessage(
                id = "",
                conversationId = "conversationId",
                message = it,
                origin = MessageOrigin.SENT,
                timestamp = 0
            )
        )
    }
}

@Composable
fun ChatContent(state: ChatState, onSendMessage: (String) -> Unit) {
    val (text, setText) = remember { mutableStateOf("") }

    Column(modifier = androidx.compose.ui.Modifier.fillMaxWidth()) {
        Column(modifier = androidx.compose.ui.Modifier.weight(1f)) {
            state.chats.forEach { chat ->
                Text(
                    text = chat.message,
                    modifier = androidx.compose.ui.Modifier.padding(8.dp)
                )
            }
        }

        Row(modifier = androidx.compose.ui.Modifier.fillMaxWidth().padding(8.dp)) {
            TextField(
                value = text,
                onValueChange = setText,
                label = { Text("New Chat") },
                modifier = androidx.compose.ui.Modifier.weight(1f).padding(end = 8.dp)
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    onSendMessage(text)
                    setText("")
                }
            }) { Text("Send") }
        }
    }
}


@Preview
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}
