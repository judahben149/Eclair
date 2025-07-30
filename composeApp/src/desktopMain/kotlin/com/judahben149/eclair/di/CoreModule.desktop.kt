package com.judahben149.eclair.di

import com.judahben149.eclair.core.Platform
import com.judahben149.eclair.core.ml.MLEngine
import com.judahben149.eclair.core.ml.MockMLEngine
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import com.judahben149.eclair.data.preferences.createDataStore
import org.koin.dsl.module

fun platformModule() = module {
    single<EclairDatabase> { getEclairDatabaseBuilder() }
    single<Platform> { Platform() }
}

val dataStoreModule = module {
    single { createDataStore() }
    single { PreferencesDataStore(get()) }
}

val mlModule = module {
    single<MLEngine> { MockMLEngine() }
}