package com.judahben149.eclair.data.llm.impl

import com.judahben149.eclair.core.Platform
import com.judahben149.eclair.core.ml.MLEngine
import com.judahben149.eclair.core.utils.logI
import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceType
import com.judahben149.eclair.domain.enums.PlatformType
import com.judahben149.eclair.domain.model.ChatMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class OnDeviceLLMService(
    private val mlEngine: MLEngine,
    private val platform: Platform
): LLMService {

    init {
        "Initializing OnDeviceLLMService".logI()
    }

    override suspend fun sendMessage(
        message: String,
        conversationHistory: List<ChatMessage>,
        coroutineScope: CoroutineScope
    ): Flow<String> {

        mlEngine.loadModel(coroutineScope).also {
            "Model loaded".logI()
        }

        val result = mlEngine.predict(coroutineScope, floatArrayOf(1f, 2f, 3f))
        result.toString().logI()
        return emptyFlow()
    }

    override fun isAvailable(): Boolean {
        return platform.type != PlatformType.Desktop
    }

    override val serviceType: LLMServiceType
        get() = LLMServiceType.ON_DEVICE
}