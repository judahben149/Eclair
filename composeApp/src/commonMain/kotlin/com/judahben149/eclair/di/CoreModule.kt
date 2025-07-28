package com.judahben149.eclair.di

import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceManager
import com.judahben149.eclair.data.llm.impl.ApiLLMService
import com.judahben149.eclair.data.llm.impl.DummyLLMService
import com.judahben149.eclair.data.llm.impl.OnDeviceLLMService
import com.judahben149.eclair.data.repository.ChatRepositoryImpl
import com.judahben149.eclair.domain.repository.ChatRepository
import com.judahben149.eclair.domain.usecase.ObserveAllChatsUseCase
import com.judahben149.eclair.domain.usecase.SaveChatUseCase
import com.judahben149.eclair.domain.usecase.SendChatMessageToLLMUseCase
import com.judahben149.eclair.presentation.screens.chat.ChatViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::ChatRepositoryImpl).bind(ChatRepository::class)
}

val useCaseModule = module {
    singleOf(::SaveChatUseCase)
    singleOf(::ObserveAllChatsUseCase)
    singleOf(::SendChatMessageToLLMUseCase)
}

val viewModelModule = module {
    factory { ChatViewModel(get(), get(), get()) }
}

val serviceModule = module {
    singleOf(::DummyLLMService).bind(LLMService::class)
    singleOf(::OnDeviceLLMService).bind(LLMService::class)
    singleOf(::ApiLLMService).bind(LLMService::class)
    singleOf(::LLMServiceManager)
}

val sharedModules = listOf(
    repositoryModule, useCaseModule, viewModelModule, serviceModule
)