package com.judahben149.eclair.data.llm.impl

import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApiLLMService(
//    private val httpClient: HttpClient,
//    private val apiConfig: ApiConfig
) : LLMService {
    
    override suspend fun sendMessage(
        message: String, 
        conversationHistory: List<ChatMessage>
    ): Flow<String> = flow {
//        val request = buildApiRequest(message, conversationHistory)
//
//        httpClient.preparePost(apiConfig.endpoint) {
//            setBody(request)
//        }.execute { response ->
//            response.bodyAsChannel().consumeEachBufferRange { buffer, _ ->
//                val chunk = buffer.readText()
//                emit(parseStreamChunk(chunk))
//            }
//        }
    }
    
//    override fun isAvailable(): Boolean = apiConfig.isConfigured()
    override fun isAvailable(): Boolean = false
    override val serviceType = LLMServiceType.API
}