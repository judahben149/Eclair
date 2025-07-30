package com.judahben149.eclair.di

import com.judahben149.eclair.core.ml.MLEngine
import com.judahben149.eclair.core.ml.MLEngineImpl
import org.koin.dsl.module

val mobileModule = module {
    single<MLEngine> { MLEngineImpl(get()) }
}