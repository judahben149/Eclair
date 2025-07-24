package com.judahben149.eclair.di

import com.judahben149.eclair.data.repository.ChatRepositoryImpl
import com.judahben149.eclair.domain.repository.ChatRepository
import com.judahben149.eclair.domain.usecase.SaveChatUseCase
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(databaseModule, repositoryModule, platformModule())
    }

val databaseModule = module {

}

val repositoryModule = module {
    singleOf(::ChatRepositoryImpl).bind(ChatRepository::class)
}

val useCaseModule = module {
    singleOf(::SaveChatUseCase)
}