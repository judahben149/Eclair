package com.judahben149.eclair.di

import com.judahben149.eclair.data.repository.ChatRepositoryImpl
import com.judahben149.eclair.domain.repository.ChatRepository
import com.judahben149.eclair.domain.usecase.ObserveAllChatsUseCase
import com.judahben149.eclair.domain.usecase.SaveChatUseCase
import com.judahben149.eclair.presentation.screens.chat.ChatViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::ChatRepositoryImpl).bind(ChatRepository::class)
}

val useCaseModule = module {
    singleOf(::SaveChatUseCase)
    singleOf(::ObserveAllChatsUseCase)
}

val viewModelModule = module {
    factory { ChatViewModel(get(), get()) }
}

val sharedModules = listOf(
    repositoryModule, useCaseModule, viewModelModule
)