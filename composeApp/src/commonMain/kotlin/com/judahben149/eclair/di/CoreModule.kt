package com.judahben149.eclair.di

//import com.cactus.CactusLM
import com.judahben149.eclair.core.ml.EquipmentClassifier
import com.judahben149.eclair.core.ml.MLEngine
import com.judahben149.eclair.core.ml.MLEngineImpl
import com.judahben149.eclair.data.llm.LLMService
import com.judahben149.eclair.data.llm.LLMServiceManager
import com.judahben149.eclair.data.llm.impl.ApiLLMService
import com.judahben149.eclair.data.llm.impl.DummyLLMService
import com.judahben149.eclair.data.llm.impl.OnDeviceLLMService
import com.judahben149.eclair.data.remote.api.ConceptApiService
import com.judahben149.eclair.data.remote.api.ConceptApiServiceImpl
import com.judahben149.eclair.data.remote.api.createHttpClient
import com.judahben149.eclair.data.repository.ChatRepositoryImpl
import com.judahben149.eclair.data.repository.ConceptRepositoryImpl
import com.judahben149.eclair.domain.repository.ChatRepository
import com.judahben149.eclair.domain.repository.ConceptRepository
import com.judahben149.eclair.domain.usecase.ObserveAllChatsUseCase
import com.judahben149.eclair.domain.usecase.SaveChatUseCase
import com.judahben149.eclair.domain.usecase.SendChatMessageToLLMUseCase
import com.judahben149.eclair.presentation.screens.chat.ChatViewModel
import com.judahben149.eclair.presentation.screens.studio.EquipmentIdentificationViewModel
import com.judahben149.eclair.presentation.screens.train.ConceptDetailViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val repositoryModule = module {
    singleOf(::ChatRepositoryImpl).bind(ChatRepository::class)
    singleOf(::ConceptRepositoryImpl).bind(ConceptRepository::class)
}

val useCaseModule = module {
    singleOf(::SaveChatUseCase)
    singleOf(::ObserveAllChatsUseCase)
    singleOf(::SendChatMessageToLLMUseCase)
}

val viewModelModule = module {
    factory { ChatViewModel(get(), get(), get()) }
    factory { EquipmentIdentificationViewModel(get()) }
    factory { (conceptId: Int) -> ConceptDetailViewModel(get(), conceptId) }
}

val serviceModule = module {
    singleOf(::DummyLLMService).bind(LLMService::class)
    singleOf(::OnDeviceLLMService).bind(LLMService::class)
    single<LLMService>{ OnDeviceLLMService(get(), get()) }
    singleOf(::ApiLLMService).bind(LLMService::class)
    singleOf(::LLMServiceManager)
//    single { CactusLM() }

    // Concept API
    single { createHttpClient() }
    single<ConceptApiService> { ConceptApiServiceImpl(get()) }
}

val mlModule = module {
    single<MLEngine> {
        MLEngineImpl(
//            get()
        )
    }
}

val sharedModules = listOf(
    repositoryModule, useCaseModule, viewModelModule, serviceModule, mlModule
)
