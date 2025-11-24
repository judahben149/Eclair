package com.judahben149.eclair.di

import com.judahben149.eclair.core.Platform
import com.judahben149.eclair.core.ml.EquipmentClassifier
import com.judahben149.eclair.data.local.EclairDatabase
import com.judahben149.eclair.data.local.getEclairDatabaseBuilder
import com.judahben149.eclair.data.preferences.PreferencesDataStore
import com.judahben149.eclair.data.preferences.createDataStore
import org.koin.dsl.module

fun platformModule() = module {
    single<EclairDatabase> { getEclairDatabaseBuilder() }
    single { get<EclairDatabase>().chatDao() }
    single { get<EclairDatabase>().conceptDao() }
    single { get<EclairDatabase>().conceptListDao() }
    single<Platform> { Platform() }
    single { EquipmentClassifier() }
}

val dataStoreModule = module {
    single { createDataStore() }
    single { PreferencesDataStore(get()) }
}