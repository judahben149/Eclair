package com.judahben149.eclair.di

import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import com.judahben149.eclair.data.preferences.createDataStore
import org.koin.dsl.module

fun platformModule() = module {
    single<EclairDatabase> { getEclairDatabaseBuilder() }
}

val dataStoreModule = module {
    single { createDataStore() }
    single { PreferencesDataStore(get()) }
}